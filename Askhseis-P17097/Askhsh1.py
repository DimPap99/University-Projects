#used python 3.6.3
import random
#numbers picked
n=0
seeguesses=str(input("You want to see the guesses of each player?Press yes or no."))
while seeguesses !="yes" and seeguesses !="no" :
    print("Invalid input.")
    seeguesses=str(input("You want to see the guesses of each player?Press yes or no."))
for z in range(1,1001):

    #generating the bingo numbers ranging from 1 to 80 in a list of strings
    BingoNumbers=list(map(str,range(1,81)))
    ls=list(map(str,range(1,81)))
    random.shuffle(ls)
    #initialise a dictionary for the players
    players={}
    #A dictionary with the same keys as players{} dictionary and values the succesful guesses of the random number
    succesfulguesses={}
    for i in range(1,101,1):
        randomlist=list(map(str,range(1,81)))
        random.shuffle(randomlist)
        #initialise the values of the guesses of each player
        succesfulguesses['{0}{1}'.format("player",i)]='0'
        #a list[] will be used as the value of the dictionary players{}
        PlayerNumbers=[]
        for j in range(0,5):

            PlayerNumbers.append(randomlist.pop())


        #entry of the list[] of PlayerNumbers in players{} dictionary
        players['{0}{1}'.format("player",i)]=PlayerNumbers
    #print(players)
    #a variable to stop the loop once the number of succesful guess is equal to 011111
    stoptheloop=False

    #loop 80 times for 80  numbers
    for i in ls:
        #pick a random number 1-80
        randomnumber=i
        #add 1 everytime a number is picked
        n+=1
        for j in range(1,101):

            #for everyplayer iterate over the values of players{}.If the number matches a value add 1 in succesfulguesses{} values
            if randomnumber in players['{0}{1}'.format("player",j)]:
                succesfulguesses['{0}{1}'.format("player",j)]+=str(1)
                if succesfulguesses['{0}{1}'.format("player",j)]=='011111':
                    stoptheloop= True
                    print("PLAYER "+str(j)+" WON!" + " With the numbers: " + str(players['{0}{1}'.format("player",i)]))
                    break

        if stoptheloop==True:
            if seeguesses =="yes":
                print(succesfulguesses)
            break
MO=n/1000
print("O μέσος όρος του πόσοι αριθμοί πρέπει να αναγγελθούν ώστε να έχουμε Bingo σε 1000 παιχνιδια ειναι:"+str(MO) )
