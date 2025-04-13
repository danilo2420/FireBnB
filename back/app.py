from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/')
def helloWorld():
    return jsonify('Hello'), 200

app.run(debug=True)