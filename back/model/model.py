from sqlalchemy.orm import declarative_base
from connection import engine

Base = declarative_base()

def createTables():
    Base.metadata.create_all(engine)
    print('Tables existed or created successfully')
    print('Note: if you modified the model classes, you might need to delete the associated tables from the DB manually first')