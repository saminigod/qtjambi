import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.xmlpatterns.QXmlQuery;
import com.trolltech.qt.xmlpatterns.QXmlSerializer;


public class src_xmlpatterns_api_qabstractxmlreceiver {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
  QXmlQuery query = new QXmlQuery();
  query.setQuery("doc('index.html')/html/body/p[1]");

  QXmlSerializerPointer serializer(new QXmlSerializer(query, myOutputDevice));
  query.evaluateToReceiver(serializer);
//! [0]


    }
}
