@UI
Feature: Practice Test Automation website

@practicetestautomation @pageObjectModelPOC
Scenario: Login to application using encrypted password
Given user open the application
And input the username "student" and password "loginPassword"
And navigate to practice test exception page
And click add button and verify Row 2 input field is displayed