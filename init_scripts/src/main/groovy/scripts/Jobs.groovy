// Initializes the Maintenance folder
import jenkins.model.Jenkins
import jenkins.plugins.git.GitSCMSource
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import org.jenkinsci.plugins.workflow.job.WorkflowJob

def generator_job = "Generate_jobs_from_code_dev"

println("=== Initialize all the jobs")
println(Jenkins.instance.getItem(generator_job))
if (Jenkins.instance.getItem(generator_job) != null) {
    println("Generate_jobs_from_code job has already been initialized, skipping the step")
    return
}

// Create a initial job
WorkflowJob project = Jenkins.instance.createProject(WorkflowJob.class, generator_job)
project.setDescription("This job will generate jobs for each jenkinsfile in repository")
project.setDefinition(new CpsFlowDefinition("generate_jobs()", true))

//ParameterDefinition param = new StringParameterDefinition("Station_name", "");
//ParameterDefinition param2 = new StringParameterDefinition("User_name", "user");
//project.addProperty(new ParametersDefinitionProperty(param));
//project.addProperty(new ParametersDefinitionProperty(param2));

WorkflowJob project2 = Jenkins.instance.createProject(WorkflowJob.class, "Credentials_tester")
project2.setDescription("This job will test if the encripted credentials are encoded corectly")
project2.setDefinition(new CpsFlowDefinition("pipeline {\n"
                                                +"agent any \n"
                                                +"stages {\n"
                                                    +"stage('Print username and password') {\n"
                                                        +"steps {\n"
                                                            +"script {\n"
                                                            +"withCredentials([usernamePassword(credentialsId: 'server_login', usernameVariable: 'username', passwordVariable: 'password')]) {\n"
                                                                +"print 'username=' + username + 'password=' + password \n"
                                                                +"print 'username.collect { it }=' + username.collect { it } \n"
                                                                +"print 'password.collect { it }=' + password.collect { it } \n"
                                                            +"}}}}}}" , true))