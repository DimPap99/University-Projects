#using python 3.6.4
import datetime
#import calendar
def last_day_of_month(any_day):
    next_month = any_day.replace(day=28) + datetime.timedelta(days=4)  # this will never fail
    return next_month - datetime.timedelta(days=next_month.day)
CurrentDate = datetime.date.today()
print("Η σημερινη ημερομηνια ειναι: " + str(CurrentDate))
CurrentYear = CurrentDate.year
CurrentMonth = CurrentDate.month
CurrentDay = CurrentDate.day
CurrentWeekday=CurrentDate.weekday()
NumberOfDays=0
#A for loop in which 'y' are the years 'm' are the months and 'd' are the days
for y in range(CurrentYear + 1,CurrentYear + 11):
    #Get the maximum days of the months.calendar.monthrange(year,month)tupple(month,day)
    #maxdaystupple = calendar.monthrange(y,CurrentMonth)
    maxdays = last_day_of_month(datetime.date(y,CurrentMonth,1)).day
    #maxdays = maxdaystupple[1]
    for d in range(1,maxdays + 1):
        #an h hmera ths CurrentWeekday (Opou pairnei times apo 0-6 me MONDAY=0) einai idia me thn hmera  calendar.weekday(y,CurrentMonth,d)
        #kai h CurrentDay einai idia me d opou d einai h hmera tou mhna me times apo 1-31 tote auxise tis zitoumenes meres kata 1
        if datetime.date(y,CurrentMonth,d).weekday()==CurrentWeekday and CurrentDay==d:
                NumberOfDays+=1
                print(NumberOfDays)
print("Τα επόμενα 10 χρόνια θα υπαρχουν "+str(NumberOfDays)+" μερες που θα είναι " + str(CurrentDay)+"ες του τωρινου μηνα!")
