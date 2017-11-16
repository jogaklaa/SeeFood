import argparse
import numpy as np
import tensorflow as tf
from PIL import Image


def startup():
    sess = tf.Session()
    saver = tf.train.import_meta_graph('saved_model/model_epoch5.ckpt.meta')
    saver.restore(sess, tf.train.latest_checkpoint('saved_model/'))
    graph = tf.get_default_graph()
    x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
    keep_prob = graph.get_tensor_by_name('Placeholder:0')
    class_scores = graph.get_tensor_by_name("fc8/fc8:0")
    print("AI has been loaded, party hard!")
    return sess
