from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

URL = 'postgresql://root:root@localhost:5432/FireBnb_Database'

engine = create_engine(URL)


engine.connect()
print('Success!!!')