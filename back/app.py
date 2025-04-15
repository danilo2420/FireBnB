from flask import Flask, jsonify, request
import connection
import sys
from model import model
from model.users import User
from model.places import Place

if not connection.testConnection():
    print("Connection to the DB was not possible. Exiting program...")
    sys.exit()

model.createTables()

app = Flask(__name__)
session = connection.getConnection()

@app.route('/')
def helloWorld():

    user = session.query(User).first()
    return jsonify(user.name, user.lastName), 200

app.run(debug=True)
connection.closeConnection()