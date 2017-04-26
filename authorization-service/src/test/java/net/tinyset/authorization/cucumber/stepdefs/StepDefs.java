package net.tinyset.authorization.cucumber.stepdefs;

import net.tinyset.authorization.AuthorizationApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AuthorizationApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
