from sqlalchemy import Column, Integer, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base

class Contain(Base):
    __tablename__ = 'contains'

    favorite_list_id = Column(Integer, ForeignKey('favorite_lists.id'), primary_key=True)
    place_id = Column(Integer, ForeignKey('places.id'), primary_key=True)