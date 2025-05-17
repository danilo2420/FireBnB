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
    email = Column(String, unique=True)
    password = Column(String)

    # Relationships
    ## With places
    ### Owner relationship
    ownedPlaces = relationship('Place', back_populates='owner')

    ### Renting relationship
    #rentedPlaces = relationship('Place', secondary='rentings', back_populates='renters')
    rentings = relationship('Renting', back_populates='guest')

    ## User reviews
    host_reviews = relationship('GuestReview', foreign_keys='GuestReview.host_id', back_populates="host_user")
    guest_reviews = relationship('GuestReview', foreign_keys='GuestReview.guest_id', back_populates="guest_user")

    ## Place reviews
    place_reviews = relationship('PlaceReview', back_populates="guest")

    ## Favorite lists
    favorite_lists = relationship('FavoriteList', back_populates="user")

    #user_reviews = relationship('UserReview', back_populates='user')

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'lastName': self.lastName,
            'age': self.age,
            'nationality': self.nationality,
            'description': self.description,
            'profile_image': self.profile_image,
            'stars': self.stars,
            'email': self.email
        }