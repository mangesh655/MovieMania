package com.mk.moviemania.interactor

import javax.inject.Inject

class Interactor @Inject constructor(
    movieInteractor: MovieInteractor,
): MovieInteractor by movieInteractor