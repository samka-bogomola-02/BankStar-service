package bank.recommendationservice.fintech.service;

import static org.junit.jupiter.api.Assertions.*;

import bank.recommendationservice.fintech.exception.NullArgumentException;
import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.RuleStats;
import bank.recommendationservice.fintech.model.RuleStatsResponse;
import bank.recommendationservice.fintech.repository.DynamicRuleRepository;
import bank.recommendationservice.fintech.repository.RuleStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RuleStatsServiceTest {
    @Mock
    private RuleStatsRepository ruleStatsRepository;
    @Mock
    private DynamicRuleRepository dynamicRuleRepository;
    @InjectMocks
    private RuleStatsService ruleStatsService;

    private Long dynamicRuleId;
    private RuleStats ruleStats;
    private DynamicRule dynamicRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dynamicRuleId = 1L;
        dynamicRule = new DynamicRule();
        dynamicRule.setId(dynamicRuleId);
        ruleStats = new RuleStats();
        ruleStats.setDynamicRule(dynamicRule);
        ruleStats.setCount(0);
    }

    @Test
    void addRuleStats_ShouldAddNewRuleStats_WhenDynamicRuleIdIsValid() {
        when(dynamicRuleRepository.findById(dynamicRuleId)).thenReturn(Optional.of(dynamicRule));

        ruleStatsService.addRuleStats(dynamicRuleId);

        verify(ruleStatsRepository, times(1)).save(any(RuleStats.class));
    }

    @Test
    void addRuleStats_ShouldThrowNullArgumentException_WhenDynamicRuleIdIsNull() {
        assertThrows(NullArgumentException.class, () -> ruleStatsService.addRuleStats(null));
        verify(ruleStatsRepository, never()).save(any(RuleStats.class));
    }

    @Test
    void addRuleStats_ShouldThrowRulesNotFoundException_WhenDynamicRuleNotFound() {
        when(dynamicRuleRepository.findById(dynamicRuleId)).thenReturn(Optional.empty());

        RulesNotFoundException exception = assertThrows(RulesNotFoundException.class, () -> ruleStatsService.addRuleStats(dynamicRuleId));
        assertEquals("Динамическое правило не найдено ", exception.getMessage());
    }

    @Test
    void getAllRuleStats_ShouldReturnListOfRuleStatsDTO() {
        when(ruleStatsRepository.findAll()).thenReturn(List.of(ruleStats));

        RuleStatsResponse response = ruleStatsService.getAllRuleStats();

        assertNotNull(response);
        assertEquals(1, response.getStats().size());
        assertEquals(dynamicRuleId, response.getStats().get(0).getRuleId());
        assertEquals(0, response.getStats().get(0).getCount());
    }

    @Test
    void increaseCounter_ShouldIncreaseCount_WhenDynamicRuleExists() {
        ruleStats.setCount(1);
        when(ruleStatsRepository.findByDynamicRuleId(dynamicRuleId)).thenReturn(ruleStats);

        ruleStatsService.increaseCounter(dynamicRuleId);

        assertEquals(2, ruleStats.getCount());
        verify(ruleStatsRepository, times(1)).save(ruleStats);
    }

    @Test
    void increaseCounter_ShouldThrowNullArgumentException_WhenDynamicRuleIdIsNull() {
        assertThrows(NullArgumentException.class, () -> ruleStatsService.increaseCounter(null));
        verify(ruleStatsRepository, never()).save(any(RuleStats.class));
    }

    @Test
    void increaseCounter_ShouldThrowRulesNotFoundException_WhenDynamicRuleNotFound() {
        when(ruleStatsRepository.findByDynamicRuleId(dynamicRuleId)).thenReturn(null);

        RulesNotFoundException exception = assertThrows(RulesNotFoundException.class, () -> ruleStatsService.increaseCounter(dynamicRuleId));
        assertEquals("Статистика срабатываний по правилу не найдена ", exception.getMessage());
    }

    @Test
    void deleteRuleStats_ShouldCallDeleteByDynamicRuleId_WhenCalled() {
        doNothing().when(ruleStatsRepository).deleteByDynamicRuleId(dynamicRuleId);

        ruleStatsService.deleteRuleStats(dynamicRuleId);

        verify(ruleStatsRepository, times(1)).deleteByDynamicRuleId(dynamicRuleId);
    }

}