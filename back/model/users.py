from sqlalchemy import Column, String, Integer, Text
from sqlalchemy.orm import relationship
from model.model import Base

class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    name = Column(String)
    lastName = Column(String)
    age = Column(Integer)
    nationality = Column(String)
    description = Column(Text)
    profile_image = Column(Text)
    stars = Column(Integer)

    # Relationships
    ## With places
    ### Owner relationship
    ownedPlaces = relationship('Place', back_populates='owner')

    ### Renting relationship
    rentedPlaces = relationship('Place', secondary='rentings', back_populates='renters')

    ## User reviews
    host_reviews = relationship('GuestReview', back_populates="host_user")
    guest_reviews = relationship('GuestReview', back_populates="guest_user")

    ## Place reviews
    place_reviews = relationship('PlaceReview', back_populates="guest")

    ## Favorite lists
    favorite_lists = relationship('FavoriteList', back_populates="user")

    #user_reviews = relationship('UserReview', back_populates='user')
