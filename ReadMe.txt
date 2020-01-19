Problem Statement - Design an app to write formatted prescriptions based on dictation from doctor. Refer attachment for more detail. The app should provide facility to sign the prescription and also send to the patient directly on his phone and email id. The method of storing the medical records (EHR) should follow relevant compliance laws like HIPAA.

To achieve forgiven task we have developed a web-app and an android application. The work flow is as follows : The doctor will ask for certain keywords as specified in problem statement .The patient will give all the answers respectively .This input in form of speech is processed through and converted to required text .Then the acquired text is filtered and adjusted into their respective keywords slots. The final prescription is obtained with an available option of editing under the doctor.

<----------------------------------------------------------WEB APP------------------------------------------------------------->

Technology Stack

FrontEnd – HTML, CSS, JS

BackEnd – NodeJS
Miscellaneous - Web Speech Api

 

Walkthrough:-

At homepage a login form is visible. On logging in a page opens asking whether you want to make prescription for new or existing patient. After choosing new patient option a page with fun UI can be visible. Clicking on mic-on button it starts recording and the doctor is supposed to say the keyword as mentioned in the problem statement. As the doctor says name, the name will be printed on blank pad on right. After doctor says name, patient or doctor him/herself can say the name of patient. Then age, gender, symptoms, diagnosis and drugs.

As the above details are done the mic can be turned off by clicking on mic-off button.  Preview button on clicking opens a modal with all the values which can be edited if are wrong. The person only needs to click on the text he/she needs to change and after click on Save Changes button. Download PDF link can be used to download the pdf created.

THE WEB APP IS NOT FULLY DEVELOPED.

Some features like:-

When pdf is created the pdf is saved to doctor’s database and sent to patient’s email or whatsapp number.

The pdf will be hashed for protecting the security.

Login using OTP for security purposes.

Sign Up using doctor’s license and when the doctor’s license is checked for authorization then only the doctor will be given access to the app.

On first sign in the doctor will be asked to upload his/her Prescription Pad image with sign on it so it can be used in future pdfs created for patients.

On clicking existing patient a list of pdfs will appear with a search form which will take the name of patient and show the pdf file.

Etc are to be implemented.

<----------------------------------------------------Mobile App-------------------------------------------------------------->
Technology Stack

FrontEnd – XML
BackEnd – JAVA Advanced
DataBase - Firebase Realtime Database
Miscellaneous - Speech to text Java library

The app is almost 85% developed, some of the features are described below in the process flow.
Process flow of the android app :-
Option 1: Doctor's sign up(if not already registered on the app) >> Login >> Homepage >> Record the conversation between the doctor and the patient >> Preview and can edit the details >> Digital signature >> Saving the prescription in the database >> Patient will receive the digitally signed prescription on her/his email/phone number
Option 2: Check a patient's prescription history

Features which are to be developed in the app:-
1. Adding manual template for the prescription
2. Sharing the app
