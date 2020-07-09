# grails-ncar-extensions
Grails utilities and extensions plugin

## Development

Use `./grailsShell` to get a reasonable Grails shell (REPL).
After exiting, you will still have to `^C`.

## Deployment

Version number is set in `application.gradle` to avoid messing around in `build.gradle`.

Use `./grailsw install` to install to your local Maven repo (e.g. `~/.m2/`).
Use `./grailsw publish-plugin` to publish to the EOL internal Maven remote repo,
defined by `internalMavenPublishUrl` in `build.gradle` (you can override this
in `local.gradle`).

## License

See the Apache License 2.0 in file [LICENSE](LICENSE).

## UCAR/NCAR and naming

UCAR is the University Corporation for Atmospheric Research.
NCAR is the National Center for Atmospheric Research.
UCAR is funded by the National Science Foundation to operate NCAR.
Please visit the [UCAR website](https://www.ucar.edu/) for more information.

This project is hosted on the "NCAR" github account, which is also
used for class names, but package names are prefixed with "edu.ucar".
This follows the Java convention since the primary domain name
for NCAR is "ucar.edu" ("ncar.ucar.edu" has historically been unused).
Previous non-public versions of this project used "ncareol"
in project and class names with the "ucar.edu.eol" package,
named for an organizational divison.
The "ucar" github account is unused.
