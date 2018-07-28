package com.selfapps.a8queengame.model;

import com.selfapps.a8queengame.R;

public class Figure {
    private FigureType type;
    private Color color;


    public Figure(FigureType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public FigureType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getImg() {
        if (color.equals(Color.WHITE)) {
            switch (type) {
                case QUEEN:
                    return R.drawable.queen_w;
                case KING:
                    return R.drawable.king_w;
                case PAWN:
                    return R.drawable.pawn_w;
                case ROOK:
                    return R.drawable.rook_w;
                case OFFICER:
                    return R.drawable.officer_w;
            }
        } else {
            switch (type) {
                case QUEEN:
                    return R.drawable.queen_b;
                case KING:
                    return R.drawable.king_b;
                case PAWN:
                    return R.drawable.pawn_b;
                case ROOK:
                    return R.drawable.rook_b;
                case OFFICER:
                    return R.drawable.officer_b;
            }
        }
        return 0;
    }

}
