package com.tree.rest;

import com.tree.entity.Statement;
import com.tree.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/statement")
@CrossOrigin(origins = {"*"})
public class StatementResource {

    Logger logger = LoggerFactory.getLogger(StatementResource.class);

    @Autowired
    private StatementService statementService;

    @GetMapping
    public ResponseEntity<List<Statement>> findAllStatements(){
        return ResponseEntity.ok(statementService.getStatements());
    }

    @GetMapping(value = "/getStatementsByAccountId/{accountId}", produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<Statement>> findAllStatementsByAccountId(@PathVariable Long accountId,
                                                                        @RequestParam(required = false) String amount,
                                                                        @RequestParam(required = false) String amountTo,
                                                                        @RequestParam(required = false) String dateFieldFrom,
                                                                        @RequestParam(required = false) String dateFieldTo){
        logger.info("calling /getStatementsByAccountId/");
        return ResponseEntity.ok(statementService.getStatementsByAccountId(accountId, amount, amountTo, dateFieldFrom, dateFieldTo));
    }

}
