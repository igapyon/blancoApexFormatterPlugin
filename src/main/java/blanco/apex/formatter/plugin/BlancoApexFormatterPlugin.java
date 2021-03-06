/*
 * Copyright 2017 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blanco.apex.formatter.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import blanco.apex.formatter.cli.BlancoApexFormatterCli;
import blanco.apex.formatter.cli.BlancoApexFormatterCliSettings;

/**
 * Apex formatter maven plugin.
 * 
 * <p>
 * usage:
 * </p>
 * 
 * <code>
 * mvn jp.igapyon.blanco.apex.formatter.plugin:blancoApexFormatterPlugin:1.0.2:format
 * </code>
 */
@Mojo(name = "format")
public class BlancoApexFormatterPlugin extends AbstractMojo {
    @Parameter(property = "format.input", defaultValue = "${project.basedir}/src/main/apex")
    private File input;

    @Parameter(property = "format.output", defaultValue = "${project.build.directory}/apex-formatted")
    private File output = null;

    @Parameter(property = "format.verbose", defaultValue = "false")
    private boolean verbose = false;

    @Parameter(property = "format.isSmashWhitespace", defaultValue = "false")
    private boolean isSmashWhitespace = false;

    @Parameter(property = "format.isFormatComma", defaultValue = "true")
    private boolean isFormatComma = true;

    @Parameter(property = "format.isFormatSemicolon", defaultValue = "true")
    private boolean isFormatSemicolon = true;

    @Parameter(property = "format.isFormatIndent", defaultValue = "true")
    private boolean isFormatIndent = true;

    @Parameter(property = "format.isFormatSpecialChar", defaultValue = "true")
    private boolean isFormatSpecialChar = true;

    @Parameter(property = "format.isFormatBracket", defaultValue = "true")
    private boolean isFormatBracket = true;

    /**
     * Settings of formatter UI.
     */
    protected final BlancoApexFormatterCliSettings settings = new BlancoApexFormatterCliSettings();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            BlancoApexFormatterCli.showVersion();

            if (input.exists() == false) {
                System.err.println("      create input dir: " + input.getAbsolutePath());
                if (input.mkdirs() == false) {
                    System.err.println("      fail to create input dir: " + input.getAbsolutePath());
                }
            }

            System.err.println("      input: " + input.getAbsolutePath());
            System.err.println("      output: " + output.getCanonicalPath());

            settings.setInput(input.getCanonicalPath());
            settings.setOutput(output.getCanonicalPath());
            settings.setVerbose(verbose);

            settings.getFormatterSettings().setSmashWhitespace(isSmashWhitespace);
            settings.getFormatterSettings().setFormatComma(isFormatComma);
            settings.getFormatterSettings().setFormatSemicolon(isFormatSemicolon);
            settings.getFormatterSettings().setFormatIndent(isFormatIndent);
            settings.getFormatterSettings().setFormatSpecialChar(isFormatSpecialChar);
            settings.getFormatterSettings().setFormatBracket(isFormatBracket);

            if (BlancoApexFormatterCli.validate(settings) == false) {
                throw new IOException("Parameter error");
            }

            BlancoApexFormatterCli.process(settings);

        } catch (IOException e) {
            e.printStackTrace();
            throw new MojoExecutionException("Error processing: ", e);
        }
    }
}
