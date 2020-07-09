package grails.ncar.extensions

import grails.plugins.*

class GrailsNcarExtensionsGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "4.0.1 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Grails Ncar Extensions" // Headline display name of the plugin
    def author = "John J. Allison"
    def authorEmail = "jja@ucar.edu"
    def description = '''\
Utilities and extensions for Grails 4 projects.
Includes groovy-ncar-extensions.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "https://ncar.github.io/grails-ncar-extensions/"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "UCAR/NCAR/EOL", url: "https://data.eol.ucar.edu/" ]

    // Any additional project leaders beyond the author specified above.
    def developers = [
        //[ name: "Joe Bloggs", email: "joe@bloggs.net" ],
    ]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "https://jira.ucar.edu/browse/DATA" ]    // private
    def issueManagement = [ system: "Github Issues", url: "https://github.com/NCAR/grails-ncar-extensions/issues" ]     // public

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/NCAR/grails-ncar-extensions" ]

    Closure doWithSpring() { {->
            // TODO Implement runtime spring config (optional)
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
