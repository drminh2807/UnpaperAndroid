// Copyright © 2005-2007 Jens Gulden
// Copyright © 2011-2011 Diego Elio Pettenò
// SPDX-FileCopyrightText: 2005 The unpaper authors
//
// SPDX-License-Identifier: GPL-2.0-only

#pragma once

#include <libavutil/frame.h>

#include "constants.h"

/****************************************************************************
 * image processing functions                                               *
 ****************************************************************************/

/* --- deskewing ---------------------------------------------------------- */

float detectRotation(AVFrame *image, int mask[EDGES_COUNT]);

void rotate(const float radians, AVFrame *source, AVFrame *target);

/* --- stretching / resizing / shifting ------------------------------------ */

void stretch(int w, int h, AVFrame **image);

void resize(int w, int h, AVFrame **image);

void shift(int shiftX, int shiftY, AVFrame **image);

/* --- mask-detection ----------------------------------------------------- */

void detectMasks(AVFrame *image);

void applyMasks(int mask[MAX_MASKS][EDGES_COUNT], const int maskCount,
                AVFrame *image);

/* --- wiping ------------------------------------------------------------- */

void applyWipes(int area[MAX_MASKS][EDGES_COUNT], int areaCount,
                AVFrame *image);

/* --- mirroring ---------------------------------------------------------- */

void mirror(int directions, AVFrame *image);

/* --- flip-rotating ------------------------------------------------------ */

void flipRotate(int direction, AVFrame **image);

/* --- blackfilter -------------------------------------------------------- */

void blackfilter(AVFrame *image);

/* --- noisefilter -------------------------------------------------------- */

int noisefilter(AVFrame *image);

/* --- blurfilter --------------------------------------------------------- */

int blurfilter(AVFrame *image);

/* --- grayfilter --------------------------------------------------------- */

int grayfilter(AVFrame *image);

/* --- border-detection --------------------------------------------------- */

void centerMask(AVFrame *image, int center[COORDINATES_COUNT],
                int mask[EDGES_COUNT]);

void alignMask(int mask[EDGES_COUNT], int outside[EDGES_COUNT], AVFrame *image);

void detectBorder(int border[EDGES_COUNT], int outsideMask[EDGES_COUNT],
                  AVFrame *image);

void borderToMask(int border[EDGES_COUNT], int mask[EDGES_COUNT],
                  AVFrame *image);

void applyBorder(int border[EDGES_COUNT], AVFrame *image);
