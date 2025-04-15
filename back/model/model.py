from sqlalchemy.orm import declarative_base
from connection import engine

Base = declarative_base()

def createTables():
    '''
    from model.users import User
    from model.places import Place
    from model.rentings import Renting
    '''
    
    Base.metadata.create_all(engine)
    print('Tables existed or created successfully')
    print('Note: if you modified the model classes, you might need to delete the associated tables from the DB manually first')