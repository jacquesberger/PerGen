/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jberger.pergen.generated.node;

import org.jberger.pergen.generated.analysis.Analysis;

@SuppressWarnings("nls")
public final class TRequired extends Token
{
    public TRequired(String text)
    {
        setText(text);
    }

    public TRequired(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TRequired(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTRequired(this);
    }
}