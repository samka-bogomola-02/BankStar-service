package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import bank.recommendationservice.fintech.repository.DynamicRuleQueryRepository;
import bank.recommendationservice.fintech.repository.DynamicRuleRepository;
import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.DynamicRuleQuery;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RecommendationDynamicRuleService {
    @Autowired
    private DynamicRuleRepository dynamicRuleRepository;
    @Autowired
    private DynamicRuleQueryRepository dynamicRuleQueryRepository;


    Logger logger = LoggerFactory.getLogger(RecommendationDynamicRuleService.class);


    /**
     * Сохраняет новое правило в базе данных.
     * <p>
     * Перед сохранением правила, оно обеспечивает, что запросы правила и их аргументы должным образом связаны с
     * правилом.
     * <p>
     * Данный метод является транзакционным, что означает, что если возникнет любая ошибка во время операции
     * сохранения, операция будет отменена.
     *
     * @param rule правило, которое нужно сохранить
     * @return сохраненное правило
     * @throws RulesNotFoundException если правило не может быть сохранено
     */

    @Transactional
    public DynamicRule addRule(DynamicRule rule) {
        logger.info("Добавление нового правила: {}", rule.toString());

        if (rule.getQueries() != null) {
            rule.getQueries().forEach(query -> {
                query.setDynamicRule(rule);
            });
        }
        return dynamicRuleRepository.save(rule);
    }


    /**
     * Удаляет динамическое правило из базы данных по идентификатору.
     * <p>
     * Метод сначала пытается найти правило по переданному идентификатору.
     * Если правило не найдено, выбрасывается исключение RulesNotFoundException.
     * Все связанные с правилом запросы также удаляются из базы данных.
     * <p>
     *
     * @param id идентификатор правила, которое необходимо удалить
     * @return удаленное правило
     * @throws RulesNotFoundException если правило с указанным идентификатором не найдено
     */
    public DynamicRule deleteDynamicRule(Long id) {
        DynamicRule ruleToRemove = dynamicRuleRepository.findById(id)
                .orElseThrow(() -> new RulesNotFoundException("Правило не найдено!"));

        List<DynamicRuleQuery> queries = dynamicRuleQueryRepository.findByDynamicRuleId(id);
        dynamicRuleQueryRepository.deleteAll(queries);

        dynamicRuleRepository.delete(ruleToRemove);

        return ruleToRemove;
    }

    /**
     * Получает список всех существующих динамических правил.
     *
     * @return список динамических правил
     */

    public List<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }
}

