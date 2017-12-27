#install.packages("ROAuth", "RCurl", "stringi", "stringi")
library(ROAuth) # For oauth
library(RCurl) 
library(stringi) # for use stri_trans_nfkc
library(streamR) #For opens a connection to Twitter's Streaming API using filterStream function
library(rJava) 
library(xlsx) #load the xlsx package

## PART 1: Declare Twitter API Credentials & Create Handshake
requestURL = "https://api.twitter.com/oauth/request_token"
accessURL = "https://api.twitter.com/oauth/access_token"
authURL = "  https://api.twitter.com/oauth/authorize"
consumerKey = "" # From dev.twitter.com
consumerSecret = "" # From dev.twitter.com

my_oauth = OAuthFactory$new(consumerKey = consumerKey,
                            consumerSecret = consumerSecret,
                            requestURL = requestURL,
                            accessURL = accessURL,
                            authURL = authURL)

#Save the my_oauth data to an .Rdata file
save(my_oauth, file = "my_oauth.Rdata")
load("my_oauth.Rdata")

my_oauth$handshake(curl=getCurlHandle(), browseUrl=FALSE)

## PART 2: collect tweets from twitter streaming API.
#Add as unicode of Arabic word #To get unicode of a word using this url http://r12a.github.io/apps/conversion/

keyword= stri_trans_nfkc('\u0627\u0644\u0647\u0644\u0627\u0644')

filterStream(oauth=my_oauth, # Save tweets in a json file
             timeout = 120, track = keyword, #(timeout) in a second
             file.name = "tweets.json") # Use my_oauth file as the OAuth credentials // 

tweetsdf = parseTweets("tweets.json", simplify = TRUE) # parse the json file and save to a data frame called tweets.df
View(tweetsdf)

#save xlsx file 
write.xlsx(tweetsdf , "tweetsTest1.xlsx",sheetName = "tweets", row.names = FALSE)

#save csv file 
write.csv(tweetsdf, "tweetsTest.csv")


##################
