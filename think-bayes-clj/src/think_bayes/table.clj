(ns think-bayes.table)

(defn make-table [hypotheses]
  "Make a table. A table is set of hypotheses (and, eventually, associated probabilites) that are 
  exhaustive and mutually exclusive. (See the book \"Think Bayes\" for additional details."
  {:hypotheses hypotheses :prior (zipmap hypotheses (repeat 1))})

(defn hypotheses [table]
  "Returns the hypotheses for table."
  (:hypotheses table))

(defn add-prior [table prior]
  "Add prior (a function of a single argument, a hypothesis). Remember that a map between
   hypotheses and probabilities qualifies."
  (assoc table :prior prior))

(defn add-likelihood [table likelihood]
  "Add a likelihood function of two arguments, data and hypothesis, that calculates the likelihood
   of data given a hypothesis is true."
  (assoc table :likelihood likelihood))

(defn prior [table hypothesis]
  "Calculate the prior probability of hypothesis in table."
  ((:prior table) hypothesis))

(def probability prior)

(defn priors [table]
  "Return a PMF of all priors for table."
  (zipmap (hypotheses table) (map (partial prior table) (hypotheses table))))

(defn likelihood [table data hypothesis]
  "Calculate the likelihood of data given hypothesis in table."
  ((:likelihood table) data hypothesis))

(defn posterior [table data]
  "Calculates the posterior probabilities of seeing data in a table."
  (let [unnormalized-posteriors (map #(* (prior table %1) (likelihood table data %1))
                                     (hypotheses table))
        total-probability (reduce + unnormalized-posteriors)
        normalized-posteriors (map #(/ % total-probability) unnormalized-posteriors)]
    (-> (hypotheses table)
        (make-table)
        (add-prior (zipmap (hypotheses table) normalized-posteriors))
        (add-likelihood (:likelihood table)))))

(defn posterior-seq [table data]
  (lazy-seq
   (if (seq data)
     (cons (posterior table (first data))
           (posterior-seq (posterior table (first data)) (rest data)))
     [])))

