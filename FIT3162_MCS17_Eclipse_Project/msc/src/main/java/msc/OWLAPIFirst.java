package msc;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.reasoner.NodeSet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OWLAPIFirst {
	
	List<String> resultList;
	List<String> invalidInput;
	String queryInputString;
	boolean inputQueryValidityChecker=true;
	boolean matchFound=false;
	
	//create manager
	OWLOntologyManager manager;
	
	//create ontology
	OWLOntology ontology;
	
	// Create a reasoner
	OWLReasonerFactory reasonerFactory;
	OWLReasoner reasoner;
	
	// Get the Disease class
	OWLClass diseaseClass;
	
	// Get the individuals that are instances of the Disease class
	NodeSet<OWLNamedIndividual> diseaseIndividuals;
	
	//Create data factory
	OWLDataFactory factory;
	
	
	
	public OWLAPIFirst(String inputString) {
		//OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		//System.out.println(man.getOntologies().size());
		try {
            // Create an OWLOntologyManager instance
            manager = OWLManager.createOWLOntologyManager();

            // Load the OWL file from the specified path
            File file = new File("C:\\Users\\haohe\\Desktop\\Jin En eclipse repo\\heartdiseaseontology.owl");
            ontology = loadOntology(manager, file);

            // Query the ontology for diseases with the input symptom
//            queryDiseasesWithSymptom(manager, ontology);
            
            // Query the ontology for diseases with the class init inputString
            resultList=new ArrayList<String>();
            invalidInput=new ArrayList<String>();
            queryInputString=inputString;

            queryDiseaseWithSymptomUITextboxFullMatch(manager,ontology);
            
            

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
	
	public String getSeverity(String diseaseName) {
		for (OWLNamedIndividual currentDisease : diseaseIndividuals.getFlattened()) {
			System.out.println(currentDisease.getIRI().getFragment()+"\n");
			if (currentDisease.getIRI().getFragment()==diseaseName) {
				System.out.println(currentDisease.getIRI().getFragment()+" checked \n");

				// Get the annotations for this individual
                Collection<OWLAnnotation> annotations = EntitySearcher.getAnnotations(currentDisease, ontology).collect(Collectors.toList());

                // Get the hasDescription property
                OWLAnnotationProperty hasSeverity = manager.getOWLDataFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#hasSeverity"));

                // Iterate over all annotations
                for (OWLAnnotation annotation : annotations) {
                    // Check if this annotation is a description
                    if (annotation.getProperty().equals(hasSeverity)) {
                        // Print out the description
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            return val.getLiteral();
                        }
                    }
                }
			}
		}
		return "Severity not found"; 
	}
	
	public String getDescription(String diseaseName) {
		for (OWLNamedIndividual currentDisease : diseaseIndividuals.getFlattened()) {
			if (currentDisease.getIRI().getFragment()==diseaseName) {
				// Get the annotations for this individual
                Collection<OWLAnnotation> annotations = EntitySearcher.getAnnotations(currentDisease, ontology).collect(Collectors.toList());

                // Get the hasDescription property
                OWLAnnotationProperty hasDescription = manager.getOWLDataFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#hasDescription"));

                // Iterate over all annotations
                for (OWLAnnotation annotation : annotations) {
                    // Check if this annotation is a description
                    if (annotation.getProperty().equals(hasDescription)) {
                        // Print out the description
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            return val.getLiteral();
                        }
                    }
                }
			}
		}
		
		return "Description not found";
	}
	
	public boolean getInputQueryValidityChecker() {
		return inputQueryValidityChecker;
	}
	
	public boolean checkMatchFound() {
		return matchFound;
	}
	
	public List<String> getListOfInvalidInput() {
		return invalidInput;
	}
	
	public List<String> getResultList() {
		return resultList;
	}

    public static OWLOntology loadOntology(OWLOntologyManager manager, File file) throws OWLOntologyCreationException {
        return manager.loadOntologyFromOntologyDocument(file);
    }
    
    public void queryDiseaseWithSymptomUITextboxFullMatch(OWLOntologyManager manager, OWLOntology ontology) {
    	// Get the data factory
        factory = manager.getOWLDataFactory();

        // Create a reasoner
        reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createNonBufferingReasoner(ontology);

        // Create an OWLObjectPropertyExpression representing the hasSymptom property
        OWLObjectPropertyExpression hasSymptom = factory.getOWLObjectProperty(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#hasSymptom"));

        // Get the Disease class
        diseaseClass = factory.getOWLClass(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#Disease"));

        // Get the individuals that are instances of the Disease class
        diseaseIndividuals = reasoner.getInstances(diseaseClass, false);

        // Create a mapping from lower case symptom names to their original case in the ontology
        Map<String, String> symptomNameMapping = createSymptomNameMapping(ontology);
        
        //convert input string into list of inputs
        String[] symptomInputs = queryInputString.toLowerCase().split(",");
        String[] validSymptomInputs=queryInputString.toLowerCase().split(",");


        //loop through all diseases in the ontology model
        for (OWLNamedIndividual currentDisease : diseaseIndividuals.getFlattened()) {
        	
        	//get current disease symptoms
            NodeSet<OWLNamedIndividual> currentDiseaseSymptoms = reasoner.getObjectPropertyValues(currentDisease, hasSymptom);

            //checker if current disease has all symptoms
            boolean hasAllSymptoms = true;
            
            int count=0;
            //loop through every symptom in symptom input
        	for (String symptomInput : symptomInputs) {
        		
        		//format the current input symptom for case sensitivity
        		String symptomInputRaw=symptomInput;
        		String symptomInputMapped= symptomNameMapping.get(symptomInput.trim());
        		
        		// input symptom exists in ontology model
        		if (symptomInputMapped != null) {
        			
        			//get name reference of symptom in ontology model
                    OWLNamedIndividual symptomInputModelName = factory.getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#" + symptomInputMapped));
                    
                    //check if current input symptom item exists in list of symptoms for current disease
                    //if doesnt exist break
                    if (!currentDiseaseSymptoms.containsEntity(symptomInputModelName)) {
                        hasAllSymptoms = false;
                        break;
                    }
                        
                // input symptom DOES NOT EXIST in ontology model
                } else {
                	//input symptom did match with any diseases
                    hasAllSymptoms = false;  
                    
                    if (validSymptomInputs[count]=="") {
                    	break;
                    } else {
                    	validSymptomInputs[count]="";
                    }
                    
                    //one or more values in input query invalid
                    inputQueryValidityChecker=false;
                    
                    //add symptom that doesnt exist in ontology model to array
                    invalidInput.add(symptomInputRaw);
                }
        		
        		//increment counter
        		count+=1;
        	}
    		//disease found that matches all symptoms (current disease looped through checker for each input symptom
            if (hasAllSymptoms) {
            	
            	//at least 1 match found
                matchFound=true;
                
                //add to array
                resultList.add(currentDisease.getIRI().getFragment());
            }         	
        }  
    }
    


    public static void queryDiseasesWithSymptom(OWLOntologyManager manager, OWLOntology ontology) {
        // Get the data factory
        OWLDataFactory factory = manager.getOWLDataFactory();

        // Create a reasoner
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);

        // Create an OWLObjectPropertyExpression representing the hasSymptom property
        OWLObjectPropertyExpression hasSymptom = factory.getOWLObjectProperty(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#hasSymptom"));

        // Get the Disease class
        OWLClass diseaseClass = factory.getOWLClass(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#Disease"));

        // Get the individuals that are instances of the Disease class
        NodeSet<OWLNamedIndividual> diseaseIndividuals = reasoner.getInstances(diseaseClass, false);

        // Create a mapping from lower case symptom names to their original case in the ontology
        Map<String, String> symptomNameMapping = createSymptomNameMapping(ontology);

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        int flag = 1;

        while (true) {
            System.out.println("Enter the symptoms you want to query, separated by commas:");
            String[] symptomInputs = scanner.nextLine().toLowerCase().split(",");

            boolean foundMatch = false;

            // Iterate over all disease individuals in the ontology
            for (OWLNamedIndividual individual : diseaseIndividuals.getFlattened()) {
                // Get the values of the hasSymptom property for this individual
                NodeSet<OWLNamedIndividual> symptoms = reasoner.getObjectPropertyValues(individual, hasSymptom);

                boolean hasAllSymptoms = true;

             // Check if the individual has all the input symptoms
                for (String symptomInput : symptomInputs) {
                    // Find the original symptom name in the mapping
                    String originalSymptomName = symptomNameMapping.get(symptomInput.trim());

                    if (originalSymptomName != null) {
                        // Create an OWLNamedIndividual representing each input symptom
                        OWLNamedIndividual inputSymptom = factory.getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/qwert/ontologies/2023/8/HeartOntology#" + originalSymptomName));

                        if (!symptoms.containsEntity(inputSymptom)) {
                            hasAllSymptoms = false;
                            break;
                        }
                    } else {
                        System.out.println("The symptom " + symptomInput + " does not exist in the ontology.");
                        hasAllSymptoms = false;  // Add this line
                        flag=0;
                        break;  // Add this line
                    }
                }
                
                if (flag==0) {
                	flag=1;
                	break;
                }

                if (hasAllSymptoms) {
                    System.out.println(individual.getIRI().getFragment() + " has all input symptoms.");
                    foundMatch = true;
                } 
            }
            
            if (foundMatch) {
                break;
            } else {
                System.out.println("No match found for symptoms " + Arrays.toString(symptomInputs) + ". Please try again.");
            }
        }
    }

    public static Map<String, String> createSymptomNameMapping(OWLOntology ontology) {
        Map<String, String> symptomNameMapping = new HashMap<>();
        for (OWLNamedIndividual symptom : ontology.getIndividualsInSignature()) {
            symptomNameMapping.put(symptom.getIRI().getFragment().toLowerCase(), symptom.getIRI().getFragment());
        }
        return symptomNameMapping;
    }
    
    
    public static void main(String[] args) {
    	OWLAPIFirst classholder= new OWLAPIFirst("Nausea");
    	System.out.println(classholder.queryInputString+"\n");
    	for(int i=0;i<classholder.getResultList().size();i++){
    		System.out.println("result :"+classholder.getSeverity(classholder.getResultList().get(i))+"\n");
    	}
    	if (classholder.checkMatchFound()) {
    		System.out.println("matched");
    	}
    }
}


	