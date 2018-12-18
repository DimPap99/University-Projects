#python 3.6.4
import tweepy
def maxval(word_dictionary):
    v=list(word_dictionary.values())
    k=list(word_dictionary.keys())
    return k[v.index(max(v))]

consumer_key="DYm43XDbCbPAEt1RNiWeSSRr1"
consumer_secret="2Aw0H6Y9IobH1cy4v0F2Y9NsujBAhE737EM67nkNQ49UMA55Qc"
access_token="951246726496706561-uKTKOYTQdHuawbVmImYcXuVTsKVfWBI"
access_token_secret="AWjO95RypQ6O5d5luGsAn6zAVjad2N8MM4dH5u056qCEU"

#authorise access to Twitter on our behalf
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

api = tweepy.API(auth)

#public_tweets = api.home_timeline()


while True:
    try:
        username=str(input("Give a twitter screen name: "))
        user=api.get_user(screen_name=username)
        break
    except (tweepy.error.TweepError):
        print("You have either given a username and not a screen name or the username doesnt exist.\nExample of a screen name:\nIn the handle @a_name the screen name is:a_name")




user=api.get_user(screen_name=username)
print(user.id)
#the list with the tweets
tweetlist=[]
usertweets=api.user_timeline(screen_name=username, count=10)

for tweet in usertweets:
    #split each string so that it doesnt have a whitespace
    spl=tweet.text.split()
    tweetlist.append(spl)
    print (tweet.text)


#a list containing each word
wordslist=[]
for twt in tweetlist:
    for word in twt:
        wordslist.append(word)
print("The list of the words is:")
print(wordslist)

#a dictionary with the frequency of the words
word_dictionary={}
#checks if the word is in the dictionary it it is it adds 1 to the frequency else it adds it to the dictionary
for word in wordslist:
    if word in word_dictionary:
        word_dictionary[word]+=1
    else:
        word_dictionary[word]=1
print("The dictionary of the words is:")
print(word_dictionary)
#Finding the most frequent word with the function maxval()
print("The most popular word is: " + maxval(word_dictionary))
