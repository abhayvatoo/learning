#ifndef MAINVIEW_H
#define MAINVIEW_H

#include <QMainWindow>

namespace Ui {
class MainView;
}

class QTcpServer;
class QTcpSocket;

class MainView : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainView(QWidget *parent = nullptr);
    ~MainView();

private slots:
    void on_btnStartServer_clicked();

    void on_btnStopServer_clicked();

private:
    Ui::MainView *ui;
    QTcpServer* m_server;
    QTcpSocket* m_socket;
    bool startServer();
    void stopServer();
    void exchangeData();
    void echoReadData();
};

#endif // MAINVIEW_H
