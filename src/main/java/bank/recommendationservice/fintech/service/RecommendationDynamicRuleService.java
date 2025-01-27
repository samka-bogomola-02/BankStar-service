package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import bank.recommendationservice.fintech.interfaces.DynamicRuleRepository;
import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.DynamicRuleQuery;
import bank.recommendationservice.fintech.model.DynamicRuleQueryArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RecommendationDynamicRuleService {
    @Autowired
    private DynamicRuleRepository dynamicRuleRepository;
    Logger logger = LoggerFactory.getLogger(RecommendationDynamicRuleService.class);


    @Transactional
    public DynamicRule addRule(DynamicRule rule) {
        logger.info("Добавление нового правила: {}", rule.toString());
        List<DynamicRuleQuery> queries = rule.getQueries();
        List<DynamicRuleQuery> updatedQueries = new ArrayList<>(); // Новый список для обновленных запросов

        if (queries != null) {
            for (DynamicRuleQuery query : queries) {
                List<DynamicRuleQueryArgument> arguments = query.getArguments();
                List<DynamicRuleQueryArgument> updatedArguments = new ArrayList<>();

                if (arguments != null) {
                    updatedArguments.addAll(arguments);
                }
                // Добавление нового аргумента
                updatedArguments.add(new DynamicRuleQueryArgument());
                // Устанавливаем обновленный список аргументов
                query.setArguments(updatedArguments);
                // Устанавливаем связь с правилом
                query.setDynamicRule(rule);
                // Добавляем обновленный запрос в новый список
                updatedQueries.add(query);
            }
        }
        // После завершения итерации устанавливаем обновленные запросы к правилу
        rule.setQueries(updatedQueries);
        return dynamicRuleRepository.save(rule);
    }


    public DynamicRule deleteDynamicRule(Long id) {
        DynamicRule ruleToRemove = dynamicRuleRepository.findById(id)
                .orElseThrow(() -> new RulesNotFoundException("Правило не найдено!"));
        dynamicRuleRepository.delete(ruleToRemove);
        return ruleToRemove;
    }

    public List<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }
}

