/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
**
** This file is part of Qt Jambi.
**
** $BEGIN_LICENSE$
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
** $END_LICENSE$

**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

package com.trolltech.qt.designer;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.trolltech.qt.qtjambi.util.RetroTranslatorHelper;
import com.trolltech.qt.xml.QDomElement;

class SetPropertyHandler extends AbstractPropertyHandler {

    public Object create(QDomElement e) throws QUiLoaderException {
        String flagsValues[] = RetroTranslatorHelper.split(childStringValue(e), "|");

        Object enumsPreprocess[] = new Object[flagsValues.length];
        Class<?> enumClass = null;
        for (int i=0; i<enumsPreprocess.length; ++i) {
            enumsPreprocess[i] = EnumPropertyHandler.enumForValue(flagsValues[i]);
            if (enumsPreprocess[i] != null)
                enumClass = enumsPreprocess[i].getClass();
        }

        assert enumClass != null;

        if (enumsPreprocess.length > 0) {
            try {
                Object enumValues = Array.newInstance(enumClass, enumsPreprocess.length);
                for (int i=0; i<enumsPreprocess.length; ++i)
                    Array.set(enumValues, i, enumsPreprocess[i]);

                Method m = enumClass.getMethod("createQFlags", enumValues.getClass());
                com.trolltech.qt.QFlags<?> qflags = (com.trolltech.qt.QFlags<?>) m.invoke(enumClass, enumValues);

                return qflags.value();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
