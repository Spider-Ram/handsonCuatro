;;Defining a template for representing/modelling persons in Jess Working Memory

(deftemplate person
   	(slot name )
   	(slot gender)
   	(slot age (type INTEGER))
   	(slot partner)
)

;;Storing four persons (four facts) in Jess Working Memory via deffacts function 

(deffacts partnership
   	(person  (name Fred)  (gender  male)   (age 26)  (partner Susan))
   	(person  (name Susan) (gender female)  (age 24) (partner Fred))
   	(person  (name Andy)  (gender male)    (age 25)   (partner Sara))
	(person  (name Alice) (gender female)  (age 23)   (partner Bob))
)


;; Defining a rule for finding persons names and printing such names

(defrule my-rule1
   (person (name ?n)) => (printout t ?n  crlf ))

;; Defining a rule for finding persons ages and printing such ages

(defrule my-rule2
   (person (age ?a)) => (printout t ?a  crlf ))


;; Defining a rule for finding female persons names and printing such names

(defrule my-rule3
   (person (gender female) (name ?x)) => (printout t ?x " is female" crlf ))


;; Defining a rule for finding persons partners

(defrule my-rule4
   (person (partner ?p) (name ?n)) => (printout t ?p " is " ?n "'s partner" crlf ))


;;Defining a rule for finding and printing facts that are female persons

(defrule my-rule5
     ?p <-  (person (gender female)) => (printout t ?p " is female" crlf))



