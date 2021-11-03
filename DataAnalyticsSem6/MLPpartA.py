import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.utils import shuffle
import tensorflow as tf
from keras.utils import np_utils
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import SimpleRNN, LSTM, GRU
import matplotlib.pyplot as plt
from sklearn.metrics import confusion_matrix
from sklearn.metrics import mean_squared_error, r2_score, explained_variance_score
from sklearn.preprocessing import MinMaxScaler


def timeseries_to_supervised(df, n_in, n_out):
   agg = pd.DataFrame()

   for i in range(n_in, 0, -1):
      df_shifted = df.shift(i).copy()
      df_shifted.rename(columns=lambda x: ('%s(t-%d)' % (x, i)), inplace=True)
      agg = pd.concat([agg, df_shifted], axis=1)

   for i in range(0, n_out):
      df_shifted = df.shift(-i).copy()
      if i == 0:
         df_shifted.rename(columns=lambda x: ('%s(t)' % (x)), inplace=True)
      else:
         df_shifted.rename(columns=lambda x: ('%s(t+%d)' % (x, i)), inplace=True)
      agg = pd.concat([agg, df_shifted], axis=1)
   agg.dropna(inplace=True)
   return agg

file_name = 'data_akbilgic.xlsx'
data = pd.read_excel(file_name, header=None, skiprows=2)

data.dropna(inplace=True)
data = data.iloc[0:530]
scaler = MinMaxScaler(feature_range=(-1, 1))
feature_scaler = MinMaxScaler(feature_range=(-1, 1))

stock = data[2].to_frame(name='USD ISE')
stock_df = scaler.fit_transform(np.reshape(stock['USD ISE'].values, (stock.shape[0], 1)))
stock_df = pd.DataFrame(data=stock_df, columns=['USD ISE'])

print(stock_df)
print(stock_df)
features = data.copy()
features.columns = ['date', 'TL ISE', 'USD ISE', 'SP', 'DAX', 'FTSE', 'NIKEEI', 'BOVESPA', 'EU', 'EM']
del features['date']
del features['TL ISE']
del features['USD ISE']

scaled_features = feature_scaler.fit_transform(np.reshape(features.values, (features.shape[0], features.shape[1])))
scaled_features = pd.DataFrame(data=scaled_features, columns=features.columns)

n_in = 8
n_out = 1
superv_features = timeseries_to_supervised(scaled_features, n_in, n_out)
superv_ise = timeseries_to_supervised(stock_df, n_in, n_out)
temp = superv_ise.copy()
temp.drop(columns=['USD ISE(t)'])
print(superv_ise)
X, y =  superv_features.values, superv_ise['USD ISE(t)'].values

print(X)
print(y)

#split out dataset into train, test
train_set_sz = round(X.shape[0] * 0.6)
# test set size will be the remaining samples

features_out = n_out
print(train_set_sz)

#For X/y training np array take the rows from 0 index up to train_set_sz index
#For X,y test np arrays take the rows remaining in the dataset. From train_set_sz index til the end.
Xtr, ytr = X[:train_set_sz, :], y[:train_set_sz]
Xte, yte = X[train_set_sz:, :], y[train_set_sz:]

batch_size = 5
model = Sequential()

print("n_in: ", n_in)
print("n_out", n_out)
print("features_out" , features_out)
print("Xtr dimentions: ", Xtr.shape)
print("Batch size: ", batch_size)
print("Trainset size: " , train_set_sz)
print("Test set size: " ,Xte.shape[0] )
print(Xtr.shape[1])
model.add(Dense(units=50, input_dim=Xtr.shape[1], activation="relu"))
model.add(Dense(50, activation="relu"))
model.add(Dense(1))
model.compile(loss='mean_squared_error', optimizer='adam')
model.summary()

model.fit(Xtr, ytr, epochs=10, batch_size=batch_size, verbose=0)

trainSetPrediction = model.predict(Xtr, batch_size=batch_size)
testSetPrediction = model.predict(Xte, batch_size=batch_size)
ytr2d = np.reshape(ytr, (ytr.shape[0], features_out))
yte2d = np.reshape(yte, (yte.shape[0], features_out))
print(ytr2d.shape)

trainSetPrediction = scaler.inverse_transform(trainSetPrediction)
trainY = scaler.inverse_transform(ytr2d)
testSetPrediction = scaler.inverse_transform(testSetPrediction)
testY = scaler.inverse_transform(yte2d)

# calculate error
print("Test MSE: ", mean_squared_error(testY, testSetPrediction))
print("Test MSE: ", sum(np.square(testY-testSetPrediction))/testY.shape[0])
print('Test Explained Variance score: ', explained_variance_score(testY, testSetPrediction))
print("Test MAE: ", sum(abs(testY-testSetPrediction))/testY.shape[0])
print("Test R2: ", r2_score(testY, testSetPrediction))
print("Test R2: ", 1-(sum(np.square(testY-testSetPrediction))/sum(np.square(testY-testY.mean()))))


# Finally, we check the result in a plot.
# A vertical line in a plot identifies a splitting point between
# the training and the test part.
predicted = np.concatenate((trainSetPrediction,testSetPrediction),axis=0)

original = np.concatenate((trainY,testY),axis=0)
predicted = np.concatenate((trainSetPrediction,testSetPrediction),axis=0)
index = range(0, original.shape[0])
plt.plot(index,original, 'g')
plt.plot(index,predicted, 'r')
plt.axvline(superv_ise.index[train_set_sz], c="b")
plt.show()

