package org.esgi.el_presidente.cli;

import java.util.List;
import java.util.Scanner;

import org.esgi.el_presidente.core.events.Event;
import org.esgi.el_presidente.core.events.EventChoice;
import org.esgi.el_presidente.core.factions.Faction;
import org.esgi.el_presidente.core.factions.FactionManager;
import org.esgi.el_presidente.core.factions.FactionType;
import org.esgi.el_presidente.core.game.Game;
import org.esgi.el_presidente.core.ressources.RessourceManager;

public class Cli {

  private Game game;

  public Cli(Game game) {
    this.game = game;
  }

  public void loop() {
    Scanner input = new Scanner(System.in);
    RessourceManager ressourceManager = game.getRessourceManager();

    while (game.isNotLost()) {
      game.nextTurn();
      Event event = game.getCurrentEvent();
      displayCurrentEvent(event);
      game.triggerEventEffect(input.nextInt());
      if (game.isTheEndOfTheYear()) {
        ressourceManager.triggerEndOfYearAction();
        displayEndOfYearBilan();
        handleEndOfYearAction();
        game.triggerEndOfYearCost();
        displayEndOfYearBilan();
      }
    }
  }

  private void handleEndOfYearAction() {
    // choice
    // add action
    // loop
  }

  private void displayEndOfYearBilan() {
    Scanner scanner = new Scanner(System.in);
    String userEntry;
    System.out.println(reviewTheGame());
    System.out.println("It's the end of the year you can buy bribe and food\n");
    do {
      System.out.println("Enter \"food\" for buy food or \"faction\" to see the faction bribe sequence or q to quit\n");
      userEntry = scanner.next();
      switch (userEntry) {
        case "food":
          buyFood();
          break;
        case "faction":
          buyFaction();
          break;
        default:
          break;
      }
    } while (!userEntry.equals("q"));
  }

  private void buyFaction() {
    String factionTypeStr;
    FactionType factionType;
    FactionManager factionManager = game.getFactionManager();
    Faction faction;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("enter the type of your faction or \"q\" for exit\n");
      factionTypeStr = scanner.next();
      factionType = FactionType.fromString(factionTypeStr);
      if (factionType != null) {
        faction = factionManager.getFaction(factionType);
        // Take Effect on faction
      }
      System.out.println(factionTypeStr);
    } while (!factionTypeStr.equals("q"));
  }

  private void buyFood() {
  }

  private void displayCurrentEvent(Event event) {
    System.out.println(event.getEventDetails());
    List<EventChoice> eventChoices = event.getEventChoices();
    for (int i = 0; i < eventChoices.size(); i += 1) {
      EventChoice eventChoice = eventChoices.get(i);
      System.out.println(String.valueOf(i) + ' ' + eventChoice.getChoiceName());
    }
  }

  public String reviewTheGame() {
    RessourceManager ressourceManager = game.getRessourceManager();
    FactionManager factionManager = game.getFactionManager();
    StringBuilder gameStrBuilder = new StringBuilder();

    gameStrBuilder.append("GlobalSatisfaction: " + factionManager.getGlobalSatisfaction() + "\n");
    askForFactionsDetails(gameStrBuilder);

    gameStrBuilder.append("Money: " + ressourceManager.getMoney() + "\n");
    gameStrBuilder.append("FoodReseve: " + ressourceManager.getFoodReserves() + "\n");
    gameStrBuilder.append("Agriculture part: " + ressourceManager.getAgriculturePart() + "\n");
    gameStrBuilder.append("Industry part: " + ressourceManager.getIndustryPart() + "\n");
    return gameStrBuilder.toString();
  }

  private void askForFactionsDetails(StringBuilder gameStrBuilder) {
    if (game.isNotLost()) {
      System.out.println("Do you want to see the details of the factions ? (true/false)");
      Scanner detail = new Scanner(System.in);
      if (detail.nextBoolean()) {
        gameStrBuilder.append(reviewFaction());
      }
    } else {
      gameStrBuilder.append(reviewFaction());
    }
  }

  private String reviewFaction() {
    StringBuilder factionReview = new StringBuilder();
    FactionManager factionManager = game.getFactionManager();
    List<Faction> factionList = factionManager.getFactionList();
    for (Faction faction : factionList) {
      factionReview.append(faction);
    }
    return factionReview.toString();
  }

  public void printResult() {
    System.out.println(reviewTheGame());
    System.out.println("C'est fini");
  }
}
