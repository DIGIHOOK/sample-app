package com.sample.app.cucumber.stepdefs;

import com.sample.app.SampleAppApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SampleAppApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
