from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from db_config import *

session = None
# URL = 'postgresql://root:root@localhost:5432/FireBnB'
URL = f'postgresql://{DB_USER}:{DB_PASSWORD}@db:{DB_PORT}/{DB_NAME}'
engine = create_engine(URL)

def testConnection() -> bool:
    try:
        engine.connect()
        print('Connection test: Success')
        return True
    except Exception as e:
        print(f'Connection test: Failed. Error: {e}')
        return False

def getConnection():
    global session
    if session is None:
        _initializeConnection()
    return session

def _initializeConnection():
    global session

    engine = create_engine(URL)
    Session = sessionmaker(bind=engine)
    session = Session()
    print('Connection was initialized successfully')

def closeConnection():
    global session
    if session is not None:
        session.close()
        print('Connection closed successfully')
