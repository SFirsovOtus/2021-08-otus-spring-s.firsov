package ru.otus.spring.credit.calculator.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.credit.calculator.domain.CreditParameters;
import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;
import ru.otus.spring.credit.calculator.enums.EarlyRepaymentType;
import ru.otus.spring.credit.calculator.service.CreditService;
import ru.otus.spring.credit.calculator.service.ScheduleChainService;
import ru.otus.spring.credit.calculator.service.calculation.ScheduleCalculationService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static ru.otus.spring.credit.calculator.controller.ui.CreditController.PARAM_CREDIT_ID;
import static ru.otus.spring.credit.calculator.controller.ui.CreditController.URL_CREDITS;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final CreditService creditService;
    private final ScheduleChainService scheduleChainService;
    private final ScheduleCalculationService scheduleCalculationService;


    public static final String URL_SCHEDULE_CHAIN="/schedule-chain";
    public static final String URL_ADD_EARLY_PAYMENT="/add-early-payment";
    public static final String URL_SAVE_SCHEDULE = "/save-schedule";


    @GetMapping(URL_SCHEDULE_CHAIN)
    public String showScheduleChain(@RequestParam(PARAM_CREDIT_ID) String creditId, Model model) {
        ScheduleChain scheduleChain = scheduleChainService.findById(creditId)
                .orElse(null);

        CreditParameters creditParameters = scheduleChain.getCreditParameters();
        List<Schedule> schedules = scheduleChain.getSchedules();

        model.addAttribute("creditId", creditId);
        model.addAttribute("creditParameters", creditParameters);
        model.addAttribute("schedules", schedules);
        model.addAttribute("url_credits", URL_CREDITS);
        model.addAttribute("param_credit_id", PARAM_CREDIT_ID);
        model.addAttribute("url_add_early_payment", URL_ADD_EARLY_PAYMENT);

        return "schedule-chain";
    }

    @GetMapping(URL_ADD_EARLY_PAYMENT)
    public String addEarlyPayment(@RequestParam(PARAM_CREDIT_ID) String creditId, Model model) {
        Set<String> earlyRepaymentTypes = EarlyRepaymentType.EARLY_REPAYMENT_TYPES.keySet();

        model.addAttribute("url_schedule_chain", URL_SCHEDULE_CHAIN);
        model.addAttribute("param_credit_id", PARAM_CREDIT_ID);
        model.addAttribute("creditId", creditId);
        model.addAttribute("url_save_schedule", URL_SAVE_SCHEDULE);
        model.addAttribute("earlyRepaymentTypes", earlyRepaymentTypes);

        return "add-early-payment";
    }

    @PostMapping(URL_SAVE_SCHEDULE)
    public String saveSchedule(@RequestParam(PARAM_CREDIT_ID) String creditId, String date, String amount, String repaymentType, Principal principal) {
        LocalDate earlyRepaymentDate = LocalDate.parse(date);
        BigDecimal earlyRepaymentAmount = new BigDecimal(amount);
        EarlyRepaymentType earlyRepaymentType = EarlyRepaymentType.EARLY_REPAYMENT_TYPES.get(repaymentType);

        EarlyPayment earlyPayment = new EarlyPayment(earlyRepaymentDate, earlyRepaymentAmount, earlyRepaymentType);
        ScheduleChain scheduleChain = scheduleChainService.findById(creditId).orElse(null);
        Schedule lastSchedule = scheduleChain.getSchedules().get(scheduleChain.getSchedules().size() - 1);

        Schedule earlySchedule = scheduleCalculationService.getEarlySchedule(
                lastSchedule, earlyPayment, scheduleChain.getCreditParameters().getYearPercent());
        scheduleChain.getSchedules().add(earlySchedule);
        creditService.save(scheduleChain);

        return "redirect:" + URL_SCHEDULE_CHAIN + "?" + PARAM_CREDIT_ID + "=" + creditId;
    }

}
