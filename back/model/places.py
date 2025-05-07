from sqlalchemy import Column, Integer, Float, Enum, ForeignKey, String
from sqlalchemy.orm import relationship
from model.model import Base
from model.placeCategoryEnum import PlaceCategory

class Place(Base):
    __tablename__ = 'places'

    id = Column(Integer, primary_key=True)
    owner_id = Column(Integer, ForeignKey('users.id'))
    name = Column(String)
    #type = Column(Enum(PlaceCategory))
    type = Column(String)
    description = Column(String)
    price_per_night = Column(Float)
    stars = Column(Integer)
    
    # Relationships
    ## Owner relationship
    
    owner = relationship('User', back_populates='ownedPlaces')

    ## Renting relationship
    #renters = relationship('User', secondary='rentings', back_populates='rentedPlaces')
    rentings = relationship('Renting', back_populates='place')

    ## With image 
    images = relationship('PlaceImage', back_populates='place')

    ## With place reviews
    reviews = relationship('PlaceReview', back_populates='place')

    ## With favorite_lists
    favorite_lists = relationship('FavoriteList', secondary='contains', back_populates='places')