from sqlalchemy import Column, Integer, Float, Enum, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base
from model.placeCategoryEnum import PlaceCategory

class Place(Base):
    __tablename__ = 'places'

    id = Column(Integer, primary_key=True)
    type = Column(Enum(PlaceCategory))
    price_per_night = Column(Float)
    
    # Relationships
    owner_id = Column(Integer, ForeignKey('users.id'))
    owner = relationship('User', back_populates='ownedPlaces')