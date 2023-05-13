import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/home_admin/create_types_pages/create_elimination.dart';
import 'package:swe206/home_admin/create_types_pages/round_robin.dart';

class CreatePage extends StatefulWidget {
  const CreatePage({super.key});

  @override
  State<CreatePage> createState() => _CreatePageState();
}

class _CreatePageState extends State<CreatePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: (Text(
          "Create Tournament",
          style: h2,
        )),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const CreateEleminationPage(),
                        ),
                      );
                    },
                    child: Card(
                      elevation: 20,
                      child: Column(
                        children: [
                          Text(
                            "Elemination",
                            style: h3,
                          ),
                          ClipRRect(
                            borderRadius: BorderRadius.circular(20),
                            child: Image(
                              image: const AssetImage(
                                  "lib/assets/img/elemination.jpeg"),
                              width: MediaQuery.of(context).size.width - 100,
                              height: 200,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const CreateRoundPage(),
                        ),
                      );
                    },
                    child: Card(
                      elevation: 20,
                      child: Column(
                        children: [
                          Text(
                            "Round robin table",
                            style: h3,
                          ),
                          Padding(
                            padding: const EdgeInsets.all(20.0),
                            child: ClipRRect(
                              borderRadius: BorderRadius.circular(20),
                              child: Image(
                                image: const AssetImage(
                                    "lib/assets/img/image.png"),
                                width: MediaQuery.of(context).size.width - 100,
                                height: 200,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
