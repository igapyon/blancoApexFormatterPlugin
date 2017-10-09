# blancoApexFormatter

blancoApexFormatter is a Apax language source code formatter.
blancoApexFormatter is written in Java. blancoApexFormatter is provided as an OSS product.

## spec

blancoApexFormatter format Apax code like below:

### before (input)

```java
@isTest
public without sharing class MySimpleTest {
         static testMethod void testMain001(){
              System.assert(false,
              'First hello word written in Apex, as a error of test code.');
}
}
```

### after (formatted output)

```java
@isTest
public without sharing class MySimpleTest {
    static testMethod void testMain001() {
        System.assert(false,
            'First hello word written in Apex, as a error of test code.');
    }
}
```

## usage

### install as maven plugin

```xml
            <plugin>
                <groupId>jp.igapyon.blanco.apex.formatter.plugin</groupId>
                <artifactId>blancoApexFormatterPlugin</artifactId>
                <version>1.0.1</version>
                <configuration>
                    <verbose>true</verbose>
                    <isSmashWhitespace>true</isSmashWhitespace>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>format</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

### run on maven

```sh
mvn jp.igapyon.blanco.apex.formatter.plugin:blancoApexFormatterPlugin:1.0.0-SNAPSHOT:format
```
