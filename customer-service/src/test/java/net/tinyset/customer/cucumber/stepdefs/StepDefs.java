package net.tinyset.customer.cucumber.stepdefs;

import net.tinyset.customer.CustomerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CustomerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
