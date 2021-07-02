(deftemplate customer
  (slot customer-id)
  (multislot name)
  (multislot address)
  (slot phone)
)

(deftemplate product
  (slot part-number)
  (slot name)
  (slot category)
  (slot price)
)

(deftemplate order
  (slot order-number)
  (slot customer-id)
)

(deftemplate line-item
  (slot order-number)
  (slot part-number)
  (slot customer-id)
  (slot quantity (default 1)))

(deffacts products 
 	(product (name USBMem) (category storage) (part-number 1234) (price 199.99))
 	(product (name Amplifier) (category electronics) (part-number 2341) (price 399.99))
 	(product (name "Rubber duck") (category mechanics) (part-number 3412) (price 99.99))
)

(deffacts customers
  (customer (customer-id 101) (name joe) (address bla bla bla) (phone 3313073905))
  (customer (customer-id 102) (name mary) (address bla bla bla) (phone 333222345))
  (customer (customer-id 103) (name bob) (address bla bla bla) (phone 331567890)) 
)  	 


(deffacts orders 
	(order (order-number 300) (customer-id 102))
	(order (order-number 301) (customer-id 103))
)

(deffacts items-list
	(line-item (order-number 300) (customer-id 102) (part-number 1234))
	(line-item (order-number 301) (customer-id 103) (part-number 2341) (quantity 10))
 
)

;;Define a rule for finding those customers who have not bought nothing at all... so far
(defrule cust-not-buying
     (customer (customer-id ?id) (name ?name))
     (not (order (order-number ?order) (customer-id ?id)))
   =>
   (printout t ?name " no ha comprado... nada!" crlf))


;;Define a rule for finding which products have been bought

(defrule prods-bought
   (order (order-number ?order))
   (line-item (order-number ?order) (part-number ?part))
   (product (part-number ?part) (name ?pn))
   =>
   (printout t ?pn " was bought " crlf))


;;Define a rule for finding which products have been bought AND their quantity

(defrule prods-qty-bgt
   (order (order-number ?order))
   (line-item (order-number ?order) (part-number ?part) (quantity ?q))
   (product (part-number ?part) (name ?p) )
   =>
   (printout t ?q " " ?p " was/were bought " crlf))

;;Define a rule for finding customers and their shopping info

(defrule customer-shopping
   (customer (customer-id ?id) (name ?cn))
   (order (order-number ?order) (customer-id ?id))
   (line-item (order-number ?order) (part-number ?part))
   (product (part-number ?part) (name ?pn))
   =>
   (printout t ?cn " bought  " ?pn crlf))



;; Define a rule for texting custormers who have not bought ...

(defrule text-cust (customer (customer-id ?cid) (name ?name) (phone ?phone))
                   (not (order (order-number ?order) (customer-id ?cid)))
=>
(assert (text-customer ?name ?phone "tienes 25% desc prox compra"))
(printout t ?name " 3313073905 tienes 25% desc prox compra" ))


;; Define a rule for calling  custormers who have not bought ...
(defrule call-cust (customer (customer-id ?cid) (name ?name) (phone ?phone))
                   (not (order (order-number ?order) (customer-id ?cid)))
=>
(assert (call-customer ?name ?phone "tienes 25% desc prox compra"))
(printout t ?name " 3313073905 tienes 25% desc prox compra" ))

