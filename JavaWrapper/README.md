## Author
- L T Stein
## Date
- 01-11-2023
## Version
- '0.43-SNAPSHOT'

## Programming language
```
java version "20.0.2" 2023-07-18
Java(TM) SE Runtime Environment (build 20.0.2+9-78)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.2+9-78, mixed mode, sharing)
```

## Libraries
- weka
- java
- org

## Packages
**weka**
- weka.classifiers.Classifier;
- weka.core.SerializationHelper;
- weka.core.Instances;
- weka.core.Attribute;
- weka.core.converters.ConverterUtils;
- weka.core.Instance;

**org**
- org.apache.commons.cli.CommandLine;
- org.apache.commons.cli.*; *for broader use*

**java**
- java.util.ArrayList;
- java.util.Arrays;
- java.util.Collections;
- java.util.List;
- java.io.File;
- java.io.FileWriter;
- java.io.IOException;
- java.text.DecimalFormat;

## Name
Thema09-ML predicting parkinson dysphonia

## Description

The JavaWrapper project is for predicting parkinson dysphonia from a .arff file with attributes (columns) consisting of measured data.
Two types of .arff files can be given. 
One with class labels present that describe the status of the patient or one without class labels assigned to the instances.

This means the user can test how well the model; amplified Random Forest using bagging, performs on already diagnosed people. 
Or the user can give a file where the diagnosis is yet unknown and the model will predict instead, with a 91 percent accuracy.
In the example data folder are two files that represent these descriptions.

There are five options: help, test, accuracy, training and save.
Option help gives a message on what options there are and how to use them. Furthermore option training and accuracy can be used
together, for predicting on supervised (diagnosed) data and returning an accuracy summary of how well the model performed.
The option test can't be combined with accuracy, since there's nothing to compare with to show its performance. Instead test
returns only the predicted labels. Both the output of test as for training can be saved using option save, by
typing save name_of_new_file. The default extension is .txt and is not required to add.

These options are realised by the use of seven classes called by the main class and inside are methods applied from libraries weka (external),
org (external) and java (internal) for predicting steps, saving, storing data and writing/printing data to the user. 
Under installation will be explained how to add the external libraries.

The Main class tries to run method start and passes the command line arguments (options), if invalid options are given a help message pops up.
Method start instantiates class Controller, passes the arguments to the class property and calls the method chooseOutput which handles all
options that are first checked (boolean wise) by class ProcessCommandlineArguments. Corresponding to the returned booleans certain tasks will be executed and
its outputs printed, e.g. help is true will print a help message.

In class Controllers the method classification handles all steps needed for classification. 
Firstly a classifier object is instantiated of class LoadClassifier and
if finished a LoadTextSeparatedFile object is made for reading the given file.
By verifying the prediction option the right reader method can be called i.e. that prepares and
returns either supervised data or unsupervised data for the classifying step.

If option training or test is chosen the model and data is passed to class ClassifyData for classification. Also, when option accuracy is given alongside training,
the data and the predictions are passed to class ClassifierAccuracy, a confusion matrix and summary are made.
At the end the predictions are formatted for prettier output.

If option save is chosen, the method writeOutputAway in class Controller instantiates the class OutputFile. 
This class creates an output file with a name given by the user and then writes the output to that file.



