package msc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import java.util.Map;

class OWLAPITest {

	@Test
    public void testLoadOntology() throws OWLOntologyCreationException {
        // Arrange
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("C:\\Users\\haohe\\Desktop\\Jin En eclipse repo\\heartdiseaseontology.owl");

        // Act
        OWLOntology result = OWLAPIFirst.loadOntology(manager, file);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testCreateSymptomNameMapping() throws OWLOntologyCreationException {
        // Arrange
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("C:\\Users\\haohe\\Desktop\\Jin En eclipse repo\\heartdiseaseontology.owl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

        // Act
        Map<String, String> result = OWLAPIFirst.createSymptomNameMapping(ontology);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testQueryDiseasesWithSymptom() throws OWLOntologyCreationException {
        // Arrange
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("C:\\Users\\haohe\\Desktop\\Jin En eclipse repo\\heartdiseaseontology.owl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        
        ByteArrayInputStream in = new ByteArrayInputStream("nausea\n".getBytes());
        System.setIn(in);

        // Act and Assert
        assertDoesNotThrow(() -> OWLAPIFirst.queryDiseasesWithSymptom(manager, ontology));
    }
    
    @Test
    public void testCreateSymptomNameMappingLowerCase() throws OWLOntologyCreationException {
        // Arrange
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("C:\\Users\\haohe\\Desktop\\Jin En eclipse repo\\heartdiseaseontology.owl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

        // Act
        Map<String, String> result = OWLAPIFirst.createSymptomNameMapping(ontology);

        // Assert
        assertTrue(result.containsKey("nausea"));
        assertNotNull(result.get("nausea"));
    }
}
