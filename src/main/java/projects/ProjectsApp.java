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
  private Project curProject;
  private List<String> operations = List.of(
      "1) Add a project",
      "2) List projects",
      "3) Select a project",
      "4) Update project details"
      );
  

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

          case 1:
            addProject();
            break;
          case 2:
            listProjects();
            break;
          case 3:
            selectProject();
            break;
          case 4:
            updateProjectDetails();
            
            default:
              System.out.println("\n" + operation + " is not valid. Try again.");
              break;
        }
      } catch (Exception e) {
        System.out.println("\nError: " + e.toString() + "Try again.");
      }

    }
  }
private void deleteProject() {
  listProjects();
  
  Integer projectId = getIntInput("Enter the ID of the Project you want to delete");
  
  projectsService.deleteProject(projectId);
  System.out.println("Project " + projectId + " was deleted successfully.");
  
  if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
    curProject = null;
  }
}


  private void updateProjectDetails() {
    if(Objects.isNull(curProject)) {
      System.out.println("\nPlease select a project.");
      return;
    }
    String projectName = 
        getStringInput("Enter the project name [" 
            + curProject.getProjectName() + "]");
   
    BigDecimal estimatedHours = 
        getDecimalInput("Enter the estimated hours [" 
           + curProject.getEstimatedHours() + "]");
    
    BigDecimal actualHours =
        getDecimalInput("Enter the actual hours [" 
             + curProject.getActualHours() + "]");
    Integer difficulty =
        getIntInput("Enter the project difficulty (1-5) ["
            + curProject.getDifficulty() + "]");
    String notes = 
        getStringInput("Enter the project notes [" 
            + curProject.getNotes() + "]");
  
    Project project = new Project();
    
    project.setProjectId(curProject.getProjectId());
    project.setProjectName(Objects.isNull(projectName) 
        ? curProject.getProjectName() : projectName);
    
    project.setEstimatedHours(
        Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
    
    project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
    project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
    project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
        
    projectsService.modifyProjectDetails(project);
   
    curProject = projectsService.fetchProjectById(curProject.getProjectId());
    
  }



  private void selectProject() {
    List<Project> projects = listProjects();
    
    Integer projectId = getIntInput("Enter a project ID");
    
    curProject = null;
    
    for(Project project : projects) {
      if(project.getProjectId().equals(projectId)) {
        curProject = projectsService.fetchProjectById(projectId);
        break;
      }
      }
    if(Objects.isNull(curProject)) {
      System.out.println("\nInvalid project selected.");
    }
    }



  private List<Project> listProjects() {
    List<Project> projects = projectsService.fetchAllProjects();
    
    System.out.println("\nProjects:");
    
    projects.forEach(project -> System.out.println("   " + project.getProjectId()
       + ": " + project.getProjectName()));
    return projects;
  }



  private void addProject() {
   String name = getStringInput("Enter the project name");
   BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
   BigDecimal actualHours = getDecimalInput("Enter the actual hours");
   Integer difficulty = getIntInput("Enter the project difficulty (1-10");
   String notes = getStringInput("Enter the project notes");
   
   Project project = new Project();
   
   project.setProjectName(name);
   project.setEstimatedHours(estimatedHours);
   project.setActualHours(actualHours);
   project.setDifficulty(difficulty);
   project.setNotes(notes);
   
   Project dbProject = projectsService.addProject(project);
   System.out.println("You have sucessfully created project: " + dbProject);
   
   //curProject = projectsService.fetchProjectById(dbProject.getProjectId());
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
