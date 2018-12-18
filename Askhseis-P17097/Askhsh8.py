#using python 3.6.4
import random
numbers=list(range(-30,31,1))
random.shuffle(numbers)
#the list with the 30 random numbers
mylist=[]
pairs=[]
counter=0
for i in range(1,31):
    mylist.append(random.choice(numbers))
print("the random list is: " +str(mylist))
#every loop starts from the next index of the previous loop
for i in range(0,30):
    for j in range(0,30):
        for z in range(0,30):
            if [mylist[i],mylist[j],mylist[z]] not in pairs and (mylist[i] + mylist[j] + mylist[z])==0:
                counter+=1
                pairs.append([mylist[i],mylist[j],mylist[z]])

if counter>0:
    print("Hparxoun "+str(counter)+"zeugh ta opoia mas dinoun athroisma 0")
    print("Ta zeugh einai: "+str(pairs))
else:
    print("Den uparxoun zeugh ta opoia mas dinoun athroisma 0")
