#include "mainview.h"
#include "ui_mainview.h"
#include <QTcpServer>
#include <QTcpSocket>
#include <QMessageBox>

static quint16 PORT_NUMBER = 23;

MainView::MainView(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainView),
    m_server(new QTcpServer(this)),
    m_socket(nullptr)
{
    ui->setupUi(this);
    ui->btnStopServer->setEnabled(false);
    connect(m_server, &QTcpServer::newConnection, this, &MainView::exchangeData);
}

MainView::~MainView()
{
    delete ui;
}

void MainView::on_btnStartServer_clicked()
{
    if(startServer())
    {
        ui->btnStartServer->setEnabled(false);
        ui->btnStopServer->setEnabled(true);
    }
}

void MainView::on_btnStopServer_clicked()
{
    stopServer();
    ui->btnStartServer->setEnabled(true);
    ui->btnStopServer->setEnabled(false);
}

bool MainView::startServer(){
    bool result = m_server->listen(QHostAddress::Any, PORT_NUMBER);

    if(!result){
        QMessageBox::critical(this, tr("Echo Server"), tr("Unable to start server: %1").arg(m_server->errorString()));
        return false;
    }

    return true;
}

void MainView::stopServer(){
    m_server->close();
    if(m_socket !=nullptr && m_socket->isOpen()){
        m_socket->close();
    }
}

void MainView::exchangeData(){
    m_socket = m_server->nextPendingConnection();
    if(m_socket->isOpen()){
        connect(m_socket, &QTcpSocket::readyRead, this, &MainView::echoReadData);
    }
}

void MainView::echoReadData(){
    m_socket->write("<echoServer>\n");
    QString result;
    while(!m_socket->atEnd()){
        result.append(m_socket->readAll());
        m_socket->waitForReadyRead(200);
    }
    m_socket->write(qPrintable(result));
    m_socket->write("\n</echoServer>\n");
}
