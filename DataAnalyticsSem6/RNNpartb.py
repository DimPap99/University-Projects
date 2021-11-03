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
from sklearn.metrics import confusion_matrix, explained_variance_score
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.preprocessing import MinMaxScaler
from preprocessing import preprocess

file_name = 'data_akbilgic.xlsx'
data = pd.read_excel(file_name, header=None, skiprows=2)
data.dropna(inplace=True)
data = data.iloc[0:530]
data.columns = ['date', 'TL ISE', 'USD ISE', 'SP', 'DAX', 'FTSE', 'NIKEEI', 'BOVESPA', 'EU', 'EM']
#print(data)
rnn_input = list()
n_in = 2
n_out = 1
superv_stock, superv_features, rnn_input, index_scaler, feature_scaler = preprocess(data, n_in, n_out, True)

total_data_num = rnn_input[0]


X, y = superv_features.values, superv_stock['USD ISE(t)'].values

train_size = int(rnn_input[0] * 0.5)
test_size = rnn_input[0] - train_size

print ("Train size: %d" % train_size)
print ("Test size: %d" % (rnn_input[0] - train_size))
print("Samples: " , rnn_input[0])
xtr, ytr = X[:train_size], y[:train_size]
xte, yte = X[train_size:], y[train_size:]
print(xtr.shape, ytr.shape)
print(xte.shape, yte.shape)

training_samples = train_size
test_samples = test_size
steps = 1

features_in = rnn_input[2]
features_out = 1
xtr = np.reshape(xtr, (training_samples, steps, features_in))
ytr = np.reshape(ytr, (training_samples, steps, features_out))

print('xtr vector dimentions: ', xtr.shape, ytr.shape)
xte = np.reshape(xte, (test_samples, steps, features_in))
yte = np.reshape(yte, (test_samples, steps, features_out))
print('xte vector dimentions: ', xte.shape, yte.shape)

print(" Test samples: ", test_samples)
print(" Training samples: ", training_samples)
print("Steps: ", steps)
print("Features In: ", features_in)
print("Features Out: ", features_out)
print("n_in: ",n_in )
print("n_out: ", n_out)
batch_size = 1
model = Sequential()

model.add(SimpleRNN(units=50, batch_input_shape=(batch_size, xtr.shape[1], xtr.shape[2]), activation="relu", return_sequences=True))
model.add(Dense(50, activation="relu"))
model.add(Dense(1))
model.compile(loss='mean_squared_error', optimizer='adam')
model.summary()

model.fit(xtr,ytr, epochs=10, batch_size=batch_size, verbose=0)

trainPredict = model.predict(xtr, batch_size=batch_size)
testPredict = model.predict(xte, batch_size=batch_size)
# invert predictions
trainPredict = np.reshape(trainPredict, (training_samples*steps, features_out))
ytr2d = np.reshape(ytr, (training_samples*steps, features_out))
testPredict = np.reshape(testPredict, (test_samples*steps, features_out))
yte2d = np.reshape(yte, (test_samples*steps, features_out))
#
trainPredict = index_scaler.inverse_transform(trainPredict)
trainY = index_scaler.inverse_transform(ytr2d)
testPredict = index_scaler.inverse_transform(testPredict)
testY = index_scaler.inverse_transform(yte2d)
# calculate error
print("Test MSE: ", mean_squared_error(testY, testPredict))
print("Test MSE: ", sum(np.square(testY-testPredict))/testY.shape[0])
print("Test MAE: ", sum(abs(testY-testPredict))/testY.shape[0])
print('Test Explained Variance score: ', explained_variance_score(testY, testPredict))

print("Test R2: ", r2_score(testY, testPredict))
print("Test R2: ", 1-(sum(np.square(testY-testPredict))/sum(np.square(testY-testY.mean()))))
# Finally, we check the result in a plot.
# A vertical line in a plot identifies a splitting point between
# the training and the test part.
predicted = np.concatenate((trainPredict,testPredict),axis=0)
original = np.concatenate((trainY,testY),axis=0)
predicted = np.concatenate((trainPredict,testPredict),axis=0)
index = range(0, original.shape[0])
plt.plot(index,original, 'g')
plt.plot(index,predicted, 'r')
plt.axvline(superv_stock.index[train_size], c="b")
plt.show()