(defproject motiva/wait-for "0.3.0-SNAPSHOT"
  :description "Test helper used all over Motiva code"
  :url "https://github.com/Motiva-AI/wait-for"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]]

  :profiles {:dev {:plugins [[s3-wagon-private "1.3.1"]]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [circleci/circleci.test "0.4.1"]]
                   :source-paths ["src" "dev/src"]}}

  :aliases {"test"   ["run" "-m" "circleci.test/dir" :project/test-paths]
            "tests"  ["run" "-m" "circleci.test"]
            "retest" ["run" "-m" "circleci.test.retest"]}

  ;; Use the chained credential provider - env credentials or a profile (set
  ;; AWS_PROFILE)
  ;; https://github.com/s3-wagon-private/s3-wagon-private#aws-credential-providers
  :repositories {"private" {:url "s3p://maven-private-repo/releases/"
                            :no-auth true
                            :sign-releases false}
                 "releases" {:url           "https://clojars.org/repo"
                             :username      "motiva-ai"
                             :password      :env
                             :sign-releases false}})
