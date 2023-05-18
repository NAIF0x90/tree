package com.tree.service;

import com.tree.config.BadRequestException;
import com.tree.config.UnauthorizedRequestException;
import com.tree.entity.Account;
import com.tree.entity.Statement;
import com.tree.repository.AccountRepository;
import com.tree.repository.StatementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementService {

    Logger logger = LoggerFactory.getLogger(StatementService.class);


    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Statement> getStatementsByAccountId(Long accountId, String amount,String amountTo, String dateFieldFrom, String dateFieldTo){

        logger.info("getting statements by account id {}, and params amount {}, amountTo {}, dateFieldFrom {}, dateFieldTo {}", accountId, amount, amountTo, dateFieldFrom, dateFieldTo);

        Account account = accountRepository.findByAccountId(accountId);

        if(account == null){
            throw new BadRequestException("There is no account with this id");
        }

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = authorities.stream().findFirst().get().getAuthority();
        throwExcptionIfUserRequestWithParams(amount, amountTo, dateFieldFrom, dateFieldTo, role);

        if(role.equalsIgnoreCase("admin")){
            return getStatements(accountId, amount, amountTo, dateFieldFrom, dateFieldTo);
        }

        return getLastThreeMonthsStatments(accountId);
    }

    private List<Statement> getStatements(Long accountId, String amount, String amountTo, String dateFieldFrom, String dateFieldTo) {
        List<Statement> deletedAmountStatementsList = new ArrayList<>();
        List<Statement> deletedDateStatementsList = new ArrayList<>();
        List<Statement> statements = statementRepository.findByAccountId(accountId);

        if(amount ==null && amountTo == null && dateFieldFrom == null && dateFieldTo == null ){
            return getLastThreeMonthsStatments(accountId);
        }

        if(amount == null && amountTo != null){
            logger.error("amount not specified");
            throw new BadRequestException("You must set amount");
        }

        if(dateFieldFrom == null &&  dateFieldTo != null){
            logger.error("dateFieldFrom not specified");
            throw new BadRequestException("You must set dateFieldFrom");
        }

        statements.forEach(statement -> {
            filterStatementsByDate(dateFieldFrom, dateFieldTo, statement, deletedDateStatementsList);
            filterStatementsByAmount(amount, amountTo, statement, deletedAmountStatementsList);
        });

        deletedAmountStatementsList.forEach(statements::remove);
        deletedDateStatementsList.forEach(statements::remove);

        return statements;
    }

    private void filterStatementsByDate(String dateFieldFrom, String dateFieldTo, Statement statement, List<Statement> deletedDateStatementsList) {
        if(dateFieldFrom != null ){
            Date dateFromDb = null;
            Date dateFromParam = null;
            Date dateToParam = null;
            try {
                dateFromDb = new SimpleDateFormat("dd/MM/yyyy").parse(statement.getDateField().replace(".","/"));
                dateFromParam = new SimpleDateFormat("dd/MM/yyyy").parse(dateFieldFrom);

                if(dateFieldTo != null){
                    dateToParam = new SimpleDateFormat("dd/MM/yyyy").parse(dateFieldTo);
                }
            } catch (ParseException e) {
                logger.error("can not parse");

                e.printStackTrace();
                throw new BadRequestException("Can not parse date");

            }

            boolean isStatementDeleted = true;

            if(dateFromDb.after(dateFromParam)){
                isStatementDeleted = false;
            }

            if(dateFieldTo != null ){
                if(dateFromDb.after(dateToParam)){
                    isStatementDeleted = true;
                }
            }

            if(isStatementDeleted){
                deletedDateStatementsList.add(statement);
            }
        }
    }

    private void filterStatementsByAmount(String amount, String amountTo, Statement statement, List<Statement> deletedAmountStatementsList) {
        if(amount != null){
            double amountNumberDb = Double.parseDouble(statement.getAmount());
            double amountNumberParam = Double.parseDouble(amount);
            boolean isStatementDeleted = true;

            if(amountNumberDb >= amountNumberParam){
                isStatementDeleted = false;
            }

            if(amountTo != null){
                double amountToNumberParam = Double.parseDouble(amountTo);

                if(amountNumberDb >= amountToNumberParam){
                    isStatementDeleted = true;
                }
            }

            if(isStatementDeleted){
                deletedAmountStatementsList.add(statement);
            }
        }
    }

    private void throwExcptionIfUserRequestWithParams(String amount, String amountTo, String dateFieldFrom, String dateFieldTo, String role) {
        if(role.equalsIgnoreCase("user") && (amount != null || amountTo != null || dateFieldFrom != null || dateFieldTo != null) ){
            logger.info("Unauthorized user");
            throw new UnauthorizedRequestException("Unauthorized");
        }
    }

    private List<Statement> getLastThreeMonthsStatments(Long accountId){
        List<Statement> statements = statementRepository.findByAccountId(accountId);
        logger.info("getting the last three months statements");

        List<Statement> statementsForLastThreeMonths = new ArrayList<>();

        statements.forEach(statement -> {
            LocalDate lastThreeMonths = LocalDate.now().minusMonths(3);
            Date date;
            try {
                 date = new SimpleDateFormat("dd/MM/yyyy").parse(statement.getDateField().replace(".","/"));

            } catch (ParseException e) {
                e.printStackTrace();
                throw new BadRequestException("Can not parse date");
            }

            Date lastThreeMonthsToDate = Date.from(lastThreeMonths.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(date.after(lastThreeMonthsToDate)){
                statementsForLastThreeMonths.add(statement);
            }

        });

        return statementsForLastThreeMonths;
    }

    public List<Statement> getStatements(){

        List<Statement> statements = statementRepository.findAll();

        return statements;
    }


}
