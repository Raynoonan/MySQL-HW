package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
  private Scanner scanner = new Scanner(System.in);
  private ProjectService projectsService = new ProjectService();

  private List<String> operations = List.of("1) Create and populate all tables",
      "2) Add a project");



  public static void main(String[] args) {
    new ProjectsApp().displayMenu();


  }



  private void displayMenu() {
    boolean done = false;

    while (!done) {
        try {
          int operation = getOperation();
          
        switch (operation) {
          case -1:
            done = exitMenu();
            break;

         
            
          case 2:
            addProject();
            break;
            
            default:
              System.out.println("\n" + operation + " is not valid. Try again.");
              break;
        }
      } catch (Exception e) {
        System.out.println("\nError: " + e.toString() + "Try again.");
      }

    }
  }



  private void addProject() {
   String name = getStringInput("Enter the project name");
   BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
   BigDecimal actualHours = getDecimalInput("Enter the actual hours");
   Integer difficulty = getIntInput("Enter the project difficulty (1-10");
   String notes = getStringInput("Enter the project notes");
   
   Project project = new Project();
   
   project.setProjectName(project);
   project.setEstimatedHours(estimatedHours);
   project.setActualHours(actualHours);
   project.setDifficulty(difficulty);
   project.setNotes(notes);
   
   Project dbProject = projectsService.addProject(project);
   System.out.println("You have sucessfully created project: " + dbProject);
   
  }
private BigDecimal getDecimalInput(String prompt) {
  String input = getStringInput(prompt);
  
  if(Objects.isNull(input)) {
    return null;
  }
  try {
    return new BigDecimal(input).setScale(2);
  }
  catch(NumberFormatException e) {
    throw new DbException(input + "is not a valid decimal number.");
  }
}



  private boolean exitMenu() {
    System.out.println("\nExiting the menu. TTFN!");
    return true;
  }



  private int getOperation() {
    printOperations();
    Integer op = getIntInput("\nEnter an operation number (press Enter to quit)");

    return Objects.isNull(op) ? -1 : op;
  }



  private void printOperations() {
    System.out.println();
    System.out.println("Here's what you can do:");

    operations.forEach(op -> System.out.println("   " + op));
  }



  private Integer getIntInput(String prompt) {
    String input = getStringInput(prompt);

    if (Objects.isNull(input)) {
      return null;
    }

    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new DbException(input + " is not a valid number.");
    }


  }

  private Double getDoubleInput(String prompt) {
    String input = getStringInput(prompt);

    if (Objects.isNull(input)) {
      return null;
    }

    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      throw new DbException(input + " is not a valid number.");
    }
  }


  private String getStringInput(String prompt) {
    System.out.print(prompt + ": ");
    String line = scanner.nextLine();

    return line.isBlank() ? null : line.trim();
  }

}
