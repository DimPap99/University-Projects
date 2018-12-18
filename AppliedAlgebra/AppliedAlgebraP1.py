#Using python 3.6.4
#def cyclepermutation(permutation_dictionary,i+1):
   # stoixeio = permutation_dictionary[i]


#elegxos wste h lista na exei swsth morfh 
lista_metathesi=[]
while True:
    try:
        μ = str(input("Dwste mia metathesi tou [n] sth morfh listas apo fusikous arithmous\npou diaxwrizontai me ton xarakthra space px. (7 10 9 11 2 1 3 5 6 8 4): "))
     
        if μ[len(μ)-1]==')':
            μ=μ[:len(μ)-1]
        if μ[0]== '(' :
            μ=μ[1:]
        if μ[0] == " ":
            μ=μ[1:]
        if μ[len(μ)-1]==" ":
            μ=μ[:len(μ)-1]
        
        lista_metathesi=μ.split()
        
        lista_int=[int(i) for i in lista_metathesi]
        #if lista_int[lista_int.index(max(lista_metathesi))] != len(lista_int):
        #    raise ValueError
        if lista_int[lista_int.index(max(lista_int))] != len(lista_int):
            raise ValueError
        if lista_metathesi == []:
            raise ValueError
        checkdictionary = {}
        for number in lista_metathesi:
            int(number)
            if number in checkdictionary:
                raise ValueError
            else:
                checkdictionary[number] = 1
                     
        break
       
    except (ValueError,IndexError,NameError):
        print("Invalid Input!\nTo input pou dwsate den einai egguro.Kapoio stoixeio ths metatheshs den einai egguro eite\nto megisto stoixeio ths metatheshs den isoutai me to plithos ton stoixeiwn ths.")
        print("Dothikan oi parakatw xaraktires: ")
        
        print(lista_metathesi)


#Apanthsh sto erwthma i)
#lista_metathesi=[7, 10, 9, 11, 2, 1, 3 ,5 ,6, 8, 4]
print("To mhkos ths metatheseis μ einai : " + str(len(lista_metathesi)))

#Apanthsh sto erwthma ii)
#perasma se lexiko wste to prwto stoixeio na antistoixei sto 1 stoixeio tis listas to deutero sto deutero k.o.k(1--->1 so stoixeio listas 2---> 2o stoixeio lista klp)
inverted_lista_metathesis = []
inverted_dictionary = {}
metathesh_dictionary = {}
#xrisimopoieitai sto erwthma v)
ZeroList=[]
for i in range(1,len(lista_metathesi) + 1):
    metathesh_dictionary[i] = lista_metathesi[i - 1]
    ZeroList.append(0)
    #antistrofi tou lexikou
    inverted_dictionary[int(metathesh_dictionary[i])]=str(i)
   
#print(metathesh_dictionary)
#print(inverted_dictionary,)
inverted_lista_metathesis.append('(')

for i in range(1,len(lista_metathesi) + 1):
   inverted_lista_metathesis.append(inverted_dictionary[i])
inverted_lista_metathesis.append(')')

inverted_metathesi = " "
for i in inverted_lista_metathesis:
    inverted_metathesi =inverted_metathesi + i +" "
print("H antistrofh metathesi einai: " + inverted_metathesi)

#apantisi sto erwthma iii)
#key1--->value--->key2-->value--->key1
#bazoume irrelevant element wste o deikths ths lista na xekinaei  apo 1 kai na tairiazei me kleidi

keys=["irrelevantElement"]
value=["irrelevantElement"]
keysduplicate=["irrelevantElement"]

for key,val in metathesh_dictionary.items():
    keys.append(key)
    keysduplicate.append(key)
    value.append(int(val))

#print(keys,value)
i = 1
cp=[]
circleproduct=[]
while True:
    circle = []
    counter = 1
    while True:
        #to prwto stoixeio ths listas keys bainei mia fora sth circle kai meta afairhtai..ta upolipa stoixeia bainoun ena ena se kathe
        #epanalipsi..h epilogi ginetai analoga me to ti bike sth lista pio prin kai akolouthite auto to motibo
        #key1--->value--->key2-->value--->key1
        #kathe stoixeio afou bei sth circle diagrafetai apo thn keys otan oloklirwthei o kuklos 
        #h diadikasia ksekinaei pali apo tn arxh me to prwto stoixeio na einai to pio mikro apo auta pou exoun meinei
        #entos ths listas
        #oi listes keys,value einai ftiagmenes wste h mia na einai sundedemenh me tis times tis allis opws einai kai to lexiko metatheshs_dictionary
        #duplicatekeys gia na mn exw index error otan kanw remove pragramata 
        #i pairnei tin timi tou teleutaiou stoixeiou p epilexthike gia na ginei append wste na dwsei swsto value otan xrhsimopoieithei
        if counter == 1:
            circle.append(keysduplicate[i])
        if circle[0] != value[keysduplicate[i]]:
            circle.append(value[keysduplicate[i]])
        else:
            break
        
        i=value[keysduplicate[i]]
        
        counter+=1
    for j in circle:
        keys.remove(j)
    #print(keys)
    #print(circle)
    if len(circle) != 1:
        circleproduct.append('(')
        cp.append(circle)
        for val in circle:
            circleproduct.append(str(val))

        #circleproduct.append(temp)
        circleproduct.append(')')
    #print(circleproduct)
    if len(keys)>1:
        i=keys[1]
    if keys[0]=="irrelevantElement" and len(keys) == 1:
        break
if circleproduct == []:
    print("H metathesh den exei kuklous")
else:
    ginomeno_kuklwn=" ".join(circleproduct)
    print("Ginomeno kuklwn ths μ : " + str(ginomeno_kuklwn))
#print(cp)
#Apantisi iv)
transposition_product=[]
for element in cp:
    if len(element) > 2 :
        for i in range(1,len(element)):
            transposition_product.append('('+ str(element[0])+str(element[i])+')')
    else:
        temp=[]
        for val in element:
            temp.append(str(val))
        transposition_product.append('('+''.join(temp)+')')
print("To ginomeno twn antimetathesewn ths μ einai :" + ''.join(transposition_product))
#print(transposition_product)
#plithos antimetathesewn
transposition_length = len(transposition_product)

#Apantisi sto erwthma v)
#
Violations = value[1:]
ZeroList=[]
#print(len(Violations))

NumberOfViolations = 0
while True:
    MaxElementIndex = Violations.index(max(Violations))
    MaxElement = Violations[MaxElementIndex]
    NumberOfViolations = NumberOfViolations + len(Violations) - (MaxElementIndex + 1)
    Violations.remove(Violations[MaxElementIndex]) 
    if len(Violations) == 1:
        break
    
print("To plhthos twn parabasewn einai : " + str(NumberOfViolations))

if NumberOfViolations % 2 == 1 and transposition_length % 2 == 1:
    print("H μ einai peritth.")
else:
    print("H μ einai artia.")

    
    


