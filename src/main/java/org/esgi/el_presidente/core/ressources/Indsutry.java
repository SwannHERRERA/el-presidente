package org.esgi.el_presidente.core.ressources;

public class Indsutry {
  private double partOfIsland;
  private final double moneyProductionCoefficient;

  public Indsutry(double partOfIsland, double moneyProductionCoefficient) {
    this.partOfIsland = partOfIsland;
    this.moneyProductionCoefficient = moneyProductionCoefficient;
  }

  public double yearlyProductionOfMoney() {
    return partOfIsland * moneyProductionCoefficient;
  }
}
