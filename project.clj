(defproject motiva/wait-for "0.1.0"
  :description "Test helper used all over Motiva code"
  :url "https://github.com/Motiva-AI/wait-for"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :profiles {:dev {:plugins [[s3-wagon-private "1.3.1"]]}}
  ;; Use the chained credential provider - env credentials or a profile (set
  ;; AWS_PROFILE)
  ;; https://github.com/s3-wagon-private/s3-wagon-private#aws-credential-providers
  :repositories {"private" {:url "s3p://maven-private-repo/releases/" :no-auth true}})